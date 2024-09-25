package com.alexc.ph.onealexapp.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.data.db.todolist.TodoEntity
import com.alexc.ph.data.db.todolist.TodoListRepository
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Success
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Error
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel@Inject constructor(
    private val todoListRepository: TodoListRepository
): ViewModel() {
    val uiState: StateFlow<TodoListUiState> = todoListRepository.myTodoList
        .map { todoItems ->
            todoItems.map {
                TodoItem(
                    id = it.id,
                    name = it.title,
                    isDone = it.isDone,
                    order = it.order
                )
            }
        }
        .map<List<TodoItem>, TodoListUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addTodo(todo: String) =
        viewModelScope.launch {
            todoListRepository.add(TodoEntity(title = todo, order = 0))
        }

    fun toggleTodo(todo: TodoItem) =
        viewModelScope.launch {
            val todoItemEntity = TodoEntity(id = todo.id, title = todo.name, isDone = !todo.isDone, order = todo.order)
            todoListRepository.update(todoItemEntity)
        }

    fun removeTodo(todo: TodoItem) =
        viewModelScope.launch {
            val todoItemEntity = TodoEntity(id = todo.id, title = todo.name, isDone = todo.isDone, order = todo.order)
            todoListRepository.delete(todoItemEntity)
        }

    fun getCurrentDate(): String {
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMM dd - EEE")
        return currentDate.format(formatter)
    }
}

data class TodoItem(
    val id: Int,
    val name: String,
    val isDone: Boolean = false,
    val order: Int
)

sealed interface TodoListUiState {
    object Loading : TodoListUiState
    data class Error(val throwable: Throwable) : TodoListUiState
    data class Success(val todoList: List<TodoItem>) : TodoListUiState
}