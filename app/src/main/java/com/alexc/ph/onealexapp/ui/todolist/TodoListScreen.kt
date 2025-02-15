
package com.alexc.ph.onealexapp.ui.todolist


import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.onealexapp.ui.components.DraggableLazyColumn
import com.alexc.ph.onealexapp.ui.constants.OverlappingHeight
import org.koin.androidx.compose.koinViewModel

@Composable
fun TodoListRoot(
    todoViewModel: TodoListViewModel = koinViewModel()
) {
    val items by todoViewModel.uiState.collectAsStateWithLifecycle()
    if (items is TodoListUiState.Success) {
        val todoList = (items as TodoListUiState.Success).todoList
        val currentDate = todoViewModel.getCurrentDate()
        TodoListScreen(
            modifier = Modifier
                .fillMaxSize(),
            dateToday = currentDate,
            todoList = todoList,
            onItemClick = todoViewModel::toggleTodo,
            onItemDelete = todoViewModel::removeTodo,
            onItemDragged = todoViewModel::reorderTodoList,
            onItemDraggedEnd = todoViewModel::updateTodoList,
            onAddButtonClick = todoViewModel::addTodo
        )
    }
}

@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    dateToday: String = "",
    todoList: List<TodoItem>,
    onItemClick: (item: TodoItem) -> Unit = {},
    onItemDelete: (item: TodoItem) -> Unit = {},
    onItemDragged: (draggedIndex: Int, targetIndex: Int) -> Unit = {_, _ ->},
    onItemDraggedEnd: () -> Unit = {},
    onAddButtonClick: (todo: String) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            DraggableLazyColumn(
                modifier = Modifier.fillMaxWidth(),
                todoItems = todoList,
                onItemDragged = onItemDragged,
                onItemDraggedEnd = onItemDraggedEnd,
                overlappingElementsHeight = OverlappingHeight
            ) { item, _ ->
                TodoItem(
                    modifier = Modifier,
                    item = item,
                    onItemClick = onItemClick,
                    onItemDelete = onItemDelete
                )
            }
            TodoInputBar(
                modifier = Modifier
                    .align(Alignment.BottomStart),
                onAddButtonClick = onAddButtonClick
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    val list = listOf(
        TodoItem(1, "Learn Compose", false, 1),
        TodoItem(2, "Learn Room", false, 2),
        TodoItem(3, "Learn Kotlin", true, 3)
    )
    TodoListScreen(modifier = Modifier, dateToday = "Sept 24 - Wednesday", todoList = list)
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    val list = listOf(
        TodoItem(1, "Learn Compose", false, 1),
        TodoItem(2, "Learn Room", false, 2),
        TodoItem(3, "Learn Kotlin", true, 3)
    )
    TodoListScreen(modifier = Modifier, dateToday = "Sept 24 - Wednesday", todoList = list)
}
