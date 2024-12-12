package com.alexc.ph.onealexapp.ui.todolist

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.onealexapp.ui.constants.LargeDp
import com.alexc.ph.onealexapp.ui.constants.MediumDp
import com.alexc.ph.onealexapp.ui.constants.TodoItemHeight
import com.alexc.ph.onealexapp.ui.constants.TodoItemIconSize
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme
import com.alexc.ph.onealexapp.ui.theme.shapes


@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    item: TodoItem,
    onItemClick: (TodoItem) -> Unit = {},
    onItemDelete: (TodoItem) -> Unit = {}
) {

    val containerColor = MaterialTheme.colorScheme.primaryContainer
    val contentColor = MaterialTheme.colorScheme.primary

    val backgroundColor = if (item.isDone) containerColor.copy(alpha = 0.5f) else containerColor
    val textColor = if (item.isDone) contentColor.copy(alpha = 0.5f) else contentColor
    val textDecoration = if (item.isDone) TextDecoration.LineThrough else null
    val iconId = if (item.isDone) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank
    val iconColorFilter = if (item.isDone) ColorFilter.tint(contentColor.copy(alpha = 0.5f)) else ColorFilter.tint(contentColor)
    val iconTintColor = if (item.isDone) contentColor.copy(alpha = 0.5f) else contentColor

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(TodoItemHeight),
        elevation = CardDefaults.cardElevation(defaultElevation = LargeDp),
        shape = shapes.extraSmall
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, color = MaterialTheme.colorScheme.onTertiaryContainer)
                ) {
                    onItemClick(item)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = iconId,
                contentDescription = null,
                modifier = Modifier
                    .padding(MediumDp)
                    .size(TodoItemIconSize),
                colorFilter = iconColorFilter
            )
            Text(
                text = item.title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.labelLarge.copy(color = textColor),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textDecoration = textDecoration
            )
            IconButton(
                onClick = { onItemDelete(item) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = iconTintColor
                )
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
    OneAlexAppTheme {
        TodoItem(
            modifier = Modifier,
            item = TodoItem(1, "Learn Compose", false, order = 0)
        )
    }
}
