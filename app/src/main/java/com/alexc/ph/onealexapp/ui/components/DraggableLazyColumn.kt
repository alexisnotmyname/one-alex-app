package com.alexc.ph.onealexapp.ui.components

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.onealexapp.ui.constants.MediumDp
import com.alexc.ph.onealexapp.ui.constants.SmallDp
import com.alexc.ph.onealexapp.ui.constants.TodoItemHeight
import kotlin.math.roundToInt


@Composable
fun DraggableLazyColumn(
    modifier: Modifier = Modifier,
    todoItems: List<TodoItem>,
    onItemDragged: (draggedIndex: Int, targetIndex: Int) -> Unit = { _, _ -> },
    onItemDraggedEnd: () -> Unit = {},
    overlappingElementsHeight: Dp = 0.dp,
    content: @Composable (item: TodoItem, isDragging: Boolean) -> Unit
) {
    var draggedIndex by remember { mutableIntStateOf(-1) }
    var draggedItemOffset by remember { mutableFloatStateOf(0f) }
    var activeItemOffset by remember { mutableStateOf(Offset.Zero) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(MediumDp),
        verticalArrangement = Arrangement.spacedBy(SmallDp)
    ) {
        itemsIndexed(items = todoItems) { index, item ->
            val isDragging = draggedIndex == index
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TodoItemHeight)
                    .offset {
                        if (isDragging) IntOffset(
                            0,
                            draggedItemOffset.roundToInt()
                        ) else IntOffset.Zero
                    }
                    .pointerInput(index) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = {
                                draggedIndex = index
                                activeItemOffset = Offset.Zero
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                draggedItemOffset += dragAmount.y
                                activeItemOffset += dragAmount
                                val targetIndex = determineNewIndex(
                                    draggedIndex,
                                    activeItemOffset,
                                    todoItems.size
                                )

                                if (targetIndex != draggedIndex && targetIndex in todoItems.indices) {
                                    // Reorder the list
                                    onItemDragged(draggedIndex, targetIndex)
                                    draggedIndex = targetIndex
                                    draggedItemOffset = 0f // Reset offset for smoothness
                                    activeItemOffset = Offset.Zero
                                }
                            },
                            onDragEnd = {
                                onItemDraggedEnd()
                                draggedIndex = -1
                                draggedItemOffset = 0f
                            },
                            onDragCancel = {
                                draggedIndex = -1
                                draggedItemOffset = 0f
                            }
                        )
                    }
            ) {
                content(item, isDragging)
            }
        }
        item { Spacer(modifier = Modifier.height(overlappingElementsHeight)) }
    }
}

fun determineNewIndex(
    currentIndex: Int,
    offset: Offset,
    listSize: Int
): Int {
    val itemHeight = TodoItemHeight.value // Height of each item
    val offsetSteps = (offset.y / itemHeight).toInt()
    return (currentIndex + offsetSteps).coerceIn(0, listSize - 1)
}