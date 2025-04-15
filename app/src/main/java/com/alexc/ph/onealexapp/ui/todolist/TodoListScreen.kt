
package com.alexc.ph.onealexapp.ui.todolist

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.onealexapp.R
import com.alexc.ph.onealexapp.ui.components.OneAlexTopAppBar
import com.alexc.ph.onealexapp.ui.todolist.components.DraggableItem
import com.alexc.ph.onealexapp.ui.todolist.components.NewTaskScreen
import com.alexc.ph.onealexapp.ui.todolist.components.TodoInputBar
import com.alexc.ph.onealexapp.ui.todolist.components.TodoItem
import com.alexc.ph.onealexapp.ui.todolist.components.dragContainer
import com.alexc.ph.onealexapp.ui.todolist.components.rememberDragDropState
import org.koin.androidx.compose.koinViewModel

@Composable
fun TodoListScreenRoot(
    todoViewModel: TodoListViewModel = koinViewModel()
) {
    val state by todoViewModel.state.collectAsStateWithLifecycle()
    TodoListScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onAction = todoViewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    state: TodoListState,
    onAction: (TodoListAction) -> Unit,
) {
    val todoList = state.todoList
    var showModalBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(TodoItem()) }
    var isEditTask by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
    ) {
        OneAlexTopAppBar(
            titleRes = R.string.todo,
            searchQuery = state.searchQuery,
            onSearchQueryChange = { query ->
                onAction(TodoListAction.OnSearchQueryChange(query))
            },
            onImeSearch = {
                focusManager.clearFocus()
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
        ) {

            val listState = rememberLazyListState()
            val dragDropState =
                rememberDragDropState(
                    lazyListState = listState,
                    onMove = { fromIndex, toIndex ->
                        onAction(TodoListAction.OnTodoItemDragged(fromIndex, toIndex))
                    },
                    onDragEnd = {
                        onAction(TodoListAction.OnTodoItemDraggedEnd)
                    }
                )

            LazyColumn(
                modifier = Modifier.dragContainer(dragDropState),
                state = listState,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(todoList, key = { _, item -> item.id }) { index, item ->
                    DraggableItem(dragDropState, index) { isDragging ->
                        val elevation by animateDpAsState(if (isDragging) 4.dp else 1.dp, label = "")
                        TodoItem(
                            modifier = Modifier.fillMaxWidth(),
                            item = item,
                            elevation = elevation,
                            onCheckedChanged = { todo ->
                                onAction(TodoListAction.OnToggleTodo(todo))
                            },
                            onStoppedEditing = { todo, newTitle ->
                                onAction(TodoListAction.OnStoppedEditing(todo, newTitle))
                            },
                            onItemDelete = { todo ->
                                onAction(TodoListAction.OnRemoveTodo(todo))
                            },
                            onMoreClicked = { todo ->
                                selectedItem = todo
                                showModalBottomSheet = true
                                isEditTask = true
                            }
                        )
                    }
                }
                item { Spacer(Modifier.height(64.dp)) }
            }

            TodoInputBar(
                modifier = Modifier.align(Alignment.BottomStart),
                onAddButtonClick = { todo ->
                    selectedItem = selectedItem.copy(title = todo)
                    showModalBottomSheet = true
                }
            )
        }
    }

    if(showModalBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismissRequest = {
                showModalBottomSheet = false
                isEditTask = false
            }
        ) {
            NewTaskScreen(
                todoItem = selectedItem,
                title = if(isEditTask) stringResource(R.string.edit_item) else stringResource(R.string.add_new_item),
                navigateBack = {
                    showModalBottomSheet = false
                    isEditTask = false
                },
                onSaveClick = { todo, date ->
                    if(isEditTask) {
                        selectedItem = selectedItem.copy(
                            title = todo,
                            dateTimeDue = date
                        )
                        onAction(TodoListAction.OnEditTodo(selectedItem))
                        isEditTask = false
                        selectedItem = TodoItem()
                    } else {
                        selectedItem = TodoItem(
                            title = todo,
                            dateTimeDue = date
                        )
                        onAction(TodoListAction.OnAddTodo(selectedItem))
                        selectedItem = TodoItem()
                    }
                    showModalBottomSheet = false
                }
            )
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
private fun TodoListScreenPreview() {
    val list = listOf(
        TodoItem(1, "Learn Compose", false, 1),
        TodoItem(2, "Learn Room", false, 2),
        TodoItem(3, "Learn Kotlin", true, 3)
    )
    TodoListScreen(
        modifier = Modifier,
        state = TodoListState(todoList = list),
        onAction = {},
    )
}
