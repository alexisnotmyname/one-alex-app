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

    fun addTodo(todo: String) = viewModelScope.launch {
            todoListRepository.add(TodoItem(title = todo, order = _todoList.value.size))
        }

    fun toggleTodo(todo: TodoItem) = viewModelScope.launch {
            val todoEntity = todo.copy(isDone = !todo.isDone)
            todoListRepository.update(todoEntity)
        }

    fun removeTodo(todo: TodoItem) = viewModelScope.launch {
            todoListRepository.delete(todo)
        }

    fun updateTodoList() = viewModelScope.launch {
        val todoEntityList = _todoList.value
        todoListRepository.updateTodoItems(todoEntityList)
    }

    fun reorderTodoList(draggedIndex: Int, targetIndex: Int) {
        if(_todoList.value[draggedIndex].isDone) return
        val updatedList = _todoList.value.toMutableList().apply {
            val itemToMove = removeAt(draggedIndex)
            add(targetIndex, itemToMove)
        }.mapIndexed { index, todoItem ->
            todoItem.copy(order = index)
        }
        _todoList.value = updatedList
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