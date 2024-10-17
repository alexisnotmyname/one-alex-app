/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.onealexapp.ui.components.DraggableLazyColumn
import com.alexc.ph.onealexapp.ui.constants.OverlappingHeight
import com.alexc.ph.onealexapp.ui.theme.OneAlexAppTheme

@Composable
fun MyTodoListScreen(
    modifier: Modifier = Modifier,
    todoViewModel: TodoListViewModel = hiltViewModel()
) {
    val items by todoViewModel.uiState.collectAsStateWithLifecycle()
    if (items is TodoListUiState.Success) {
        val todoList = (items as TodoListUiState.Success).todoList
        val currentDate = todoViewModel.getCurrentDate()
        MyTodoListScreen(
            modifier = modifier,
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
fun MyTodoListScreen(
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
        modifier = modifier.fillMaxSize()
    ) {
        ToDoListHeader(
            modifier = Modifier.fillMaxWidth(),
            dateToday = dateToday
        )
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            DraggableLazyColumn(
                modifier = Modifier.fillMaxWidth(),
                todoItems = todoList,
                onItemDragged = onItemDragged,
                onItemDraggedEnd = onItemDraggedEnd,
                overlappingElementsHeight = OverlappingHeight
            ) { item, _ ->
                TodoItemUi(
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
    OneAlexAppTheme  {
        MyTodoListScreen(modifier = Modifier, dateToday = "Sept 24 - Wednesday", todoList = list)
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    val list = listOf(
        TodoItem(1, "Learn Compose", false, 1),
        TodoItem(2, "Learn Room", false, 2),
        TodoItem(3, "Learn Kotlin", true, 3)
    )
    OneAlexAppTheme {
        MyTodoListScreen(modifier = Modifier, dateToday = "Sept 24 - Wednesday", todoList = list)
    }
}
