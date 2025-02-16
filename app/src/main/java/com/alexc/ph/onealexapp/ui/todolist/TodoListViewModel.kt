package com.alexc.ph.onealexapp.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.domain.model.TodoItem
import com.alexc.ph.domain.repository.TodoListRepository
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Error
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Loading
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TodoListViewModel(
    private val todoListRepository: TodoListRepository
): ViewModel() {

    private var _todoList = MutableStateFlow<List<TodoItem>>(emptyList())

    val uiState: StateFlow<TodoListUiState> = _todoList
        .onStart { getTodoList() }
        .map<List<TodoItem>, TodoListUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)

    private fun getTodoList()  {
        viewModelScope.launch {
            todoListRepository.myTodoList.collect{
                _todoList.value = it
            }
        }
    }

    fun onAction(action: TodoListAction) {
        when(action) {
            is TodoListAction.OnAddTodo -> {
                val newTodo = TodoItem(title = action.todo, order = _todoList.value.size)
                viewModelScope.launch {
                    todoListRepository.add(newTodo)
                }
            }
            is TodoListAction.OnRemoveTodo -> {
                viewModelScope.launch {
                    todoListRepository.delete(action.todo)
                }
            }
            is TodoListAction.OnToggleTodo -> {
                viewModelScope.launch {
                    val updated = action.todo.copy(isDone = !action.todo.isDone)
                    todoListRepository.update(updated)
                }
            }
            is TodoListAction.OnStoppedEditing -> {
                viewModelScope.launch {
                    val updated = action.todo.copy(title = action.newTitle)
                    todoListRepository.update(updated)
                }
            }
            is TodoListAction.OnTodoItemDragged -> {
                if(_todoList.value[action.fromIndex].isDone) return
                val updatedList = _todoList.value.toMutableList().apply {
                    add(action.toIndex, removeAt(action.fromIndex))
                }.mapIndexed { index, todoItem ->
                    todoItem.copy(order = index)
                }
                _todoList.value = updatedList
            }
            TodoListAction.OnTodoItemDraggedEnd -> {
                viewModelScope.launch {
                    todoListRepository.updateTodoItems(_todoList.value)
                }
            }
        }
    }

    fun getCurrentDate(): String {
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMM dd - EEEE")
        return currentDate.format(formatter)
    }
}

sealed interface TodoListUiState {
    object Loading : TodoListUiState
    data class Error(val throwable: Throwable) : TodoListUiState
    data class Success(val todoList: List<TodoItem>) : TodoListUiState
}