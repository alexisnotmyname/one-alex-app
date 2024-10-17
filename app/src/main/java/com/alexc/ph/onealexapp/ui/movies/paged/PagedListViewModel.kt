package com.alexc.ph.onealexapp.ui.movies.paged

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexc.ph.domain.GetPagedContentUseCase
import com.alexc.ph.domain.model.BaseContent
import com.alexc.ph.domain.model.Category
import com.alexc.ph.onealexapp.ui.movies.paged.PagedListUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagedListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getPagedContentUseCase: GetPagedContentUseCase
): ViewModel() {
    private val category = savedStateHandle.toRoute<PagedListRoute>().category

    private val _moviesPaged: MutableStateFlow<PagingData<BaseContent>> = MutableStateFlow(PagingData.empty())
    val moviesPaged: StateFlow<PagingData<BaseContent>> get() = _moviesPaged

    val pagedListUiState: StateFlow<PagedListUiState> = _moviesPaged
        .onStart { getPagedListContent() }
        .map { pagedData ->
            Success(category)
        }
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Loading)

    fun getPagedListContent() {
        viewModelScope.launch {
            getPagedContentUseCase(category)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _moviesPaged.value = pagingData
                }
        }
    }

}

sealed interface PagedListUiState {
    object Loading : PagedListUiState
    data class Error(val throwable: Throwable) : PagedListUiState
    data class Success(
        val category: Category,
    ) : PagedListUiState
}