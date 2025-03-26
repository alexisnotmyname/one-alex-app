package com.alexc.ph.onealexapp.ui.todolist.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.onealexapp.ui.theme.shapes
import com.alexc.ph.onealexapp.ui.util.convertMillisToDate
import com.alexc.ph.onealexapp.ui.util.getCurrentDayMillis


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

    val containerColor = MaterialTheme.colorScheme.onSecondary
    val contentColor = MaterialTheme.colorScheme.primary
    val secondaryContentColor = MaterialTheme.colorScheme.secondary

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
                .fillMaxWidth()
                .background(backgroundColor),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = Icons.Default.DragIndicator,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp),
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
                ),
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 4.dp)
            ) {
                BasicTextField(
                    value = text,
                    placeHolderText = "Enter task here...",
                    modifier = Modifier
                        .padding(vertical = 4.dp)
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
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    onValueChanged = {
                        text = it
                    },
                    onStoppedEditing = {
                        onStoppedEditing(item, text)
                    }
                )

                item.dateTimeDue?.let {

                    val isOverdue = it < getCurrentDayMillis()
                    val textColorDate = when {
                        item.isDone -> secondaryContentColor.copy(alpha = 0.5f)
                        isOverdue -> Color.Red
                        else -> secondaryContentColor
                    }

                    Text(
                        text = convertMillisToDate(it),
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = textColorDate,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(end = 8.dp)
            ) {
                if(isFocused || item.isDone) {
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
        modifier = Modifier.fillMaxWidth(),
        item = TodoItem(1, "Learn Compose", false, order = 0),
        onCheckedChanged = {},
        onStoppedEditing = {_, _ ->},
        onItemDelete = {},
        onMoreClicked = {}
    )
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
fun ToDoItemUiWithDatePreview() {
    TodoItem(
        modifier = Modifier.fillMaxWidth(),
        item = TodoItem(1, "Learn Compose", false, dateTimeDue = 1739794572850, order = 0),
        onCheckedChanged = {},
        onStoppedEditing = {_, _ ->},
        onItemDelete = {},
        onMoreClicked = {}
    )
}
