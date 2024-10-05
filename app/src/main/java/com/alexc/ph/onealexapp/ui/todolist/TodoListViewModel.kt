package com.alexc.ph.onealexapp.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.data.model.todolist.TodoEntity
import com.alexc.ph.data.db.todolist.TodoListRepository
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Error
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Loading
import com.alexc.ph.onealexapp.ui.todolist.TodoListUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel@Inject constructor(
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
                _todoList.value = it.map { todoEntity ->
                    todoEntity.toTodoItem()
                }
            }
        }
    }

    fun addTodo(todo: String) = viewModelScope.launch {
            todoListRepository.add(TodoEntity(title = todo, order = _todoList.value.size))
        }

    fun toggleTodo(todo: TodoItem) = viewModelScope.launch {
            val todoEntity = todo.toTodoEntity().copy(isDone = !todo.isDone)
            todoListRepository.update(todoEntity)
        }

    fun removeTodo(todo: TodoItem) = viewModelScope.launch {
            todoListRepository.delete(todo.toTodoEntity())
        }

    fun updateTodoList() = viewModelScope.launch {
        val todoEntityList = _todoList.value.map { it.toTodoEntity() }
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

data class TodoItem(
    val id: Int,
    val name: String,
    val isDone: Boolean = false,
    val order: Int
)

fun TodoItem.toTodoEntity() = TodoEntity(id = id, title = name, isDone = isDone, order = order)
fun TodoEntity.toTodoItem() = TodoItem(id = id, name = title, isDone = isDone, order = order)

sealed interface TodoListUiState {
    object Loading : TodoListUiState
    data class Error(val throwable: Throwable) : TodoListUiState
    data class Success(val todoList: List<TodoItem>) : TodoListUiState
}