package com.example.bookshell.ui

/**
 * UI 状态枚举
 */
sealed interface BooksUiState {
    data class Success(val books: List<com.example.bookshell.model.Book>) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}
