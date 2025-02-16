package com.alexc.ph.onealexapp.ui.todolist.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.onealexapp.ui.constants.MediumDp
import com.alexc.ph.onealexapp.ui.constants.TodoItemIconSize
import com.alexc.ph.onealexapp.ui.theme.shapes


@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    item: TodoItem,
    elevation: Dp = 1.dp,
    onCheckedChanged: (TodoItem) -> Unit,
    onStoppedEditing: (TodoItem, String) -> Unit,
    onItemDelete: (TodoItem) -> Unit,
    onMoreClicked: (TodoItem) -> Unit,
) {

    var text by remember { mutableStateOf(item.title) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    DisposableEffect(Unit) {
        onDispose {
            if(isFocused) {
                onStoppedEditing(item, text)
            }
        }
    }

    val containerColor = MaterialTheme.colorScheme.surface
    val contentColor = MaterialTheme.colorScheme.primary

    val backgroundColor = if (item.isDone) containerColor.copy(alpha = 0.5f) else containerColor
    val textColor = if (item.isDone) contentColor.copy(alpha = 0.5f) else contentColor
    val textDecoration = if (item.isDone) TextDecoration.LineThrough else null
    val iconColorFilter = if (item.isDone) ColorFilter.tint(contentColor.copy(alpha = 0.5f)) else ColorFilter.tint(contentColor)
    val iconTintColor = if (item.isDone) contentColor.copy(alpha = 0.5f) else contentColor

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(elevation),
        shape = shapes.extraSmall
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                imageVector = Icons.Default.DragIndicator,
                contentDescription = null,
                modifier = Modifier
                    .padding(MediumDp)
                    .size(TodoItemIconSize),
                colorFilter = iconColorFilter
            )

            Checkbox(
                checked = item.isDone,
                onCheckedChange = {
                    onCheckedChanged(item)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = contentColor.copy(alpha = 0.5f),
                    uncheckedColor = contentColor,
                    checkmarkColor = Color.White
                )
            )

            BasicTextField(
                value = text,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { state ->
                        isFocused = state.isFocused
                        if (!state.isFocused) {
                            onStoppedEditing(item, text)
                        }
                    },
                enabled = !item.isDone,
                textDecoration = textDecoration,
                textColor = textColor,
                onValueChanged = {
                    text = it
                },
                onStoppedEditing = {
                    onStoppedEditing(item, text)
                }
            )

            if(isFocused) {
                IconButton(
                    onClick = { onItemDelete(item) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = iconTintColor
                    )
                }
            } else {
                IconButton(
                    onClick = { onMoreClicked(item) },
                    enabled = !item.isDone,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = iconTintColor
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ToDoItemUiPreview() {
    TodoItem(
        modifier = Modifier,
        item = TodoItem(1, "Learn Compose", false, order = 0),
        onCheckedChanged = {},
        onStoppedEditing = {_, _ ->},
        onItemDelete = {},
        onMoreClicked = {}
    )
}
