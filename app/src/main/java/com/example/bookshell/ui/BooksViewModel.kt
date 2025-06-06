package com.example.bookshell.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshell.BookshellApplication
import com.example.bookshell.data.BooksRepository
import kotlinx.coroutines.launch

/**
 * 图书 ViewModel，管理 UI 状态和业务逻辑
 */
class BooksViewModel(private val booksRepository: BooksRepository) : ViewModel() {
    
    /** UI 状态 */
    var uiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set
    
    /** 当前搜索查询 */
    var searchQuery by mutableStateOf("")
        private set
    
    init {
        // 初始化时搜索一些默认图书
        searchBooks("android development")
    }
    
    /**
     * 搜索图书
     */
    fun searchBooks(query: String = searchQuery) {
        viewModelScope.launch {
            uiState = BooksUiState.Loading
            uiState = try {
                val books = booksRepository.searchBooks(query)
                BooksUiState.Success(books)
            } catch (e: Exception) {
                BooksUiState.Error
            }
        }
    }
    
    /**
     * 更新搜索查询
     */
    fun updateSearchQuery(query: String) {
        searchQuery = query
    }
    
    /**
     * ViewModel 工厂
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshellApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }
}
