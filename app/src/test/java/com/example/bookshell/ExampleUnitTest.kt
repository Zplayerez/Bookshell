package com.example.bookshell

import com.example.bookshell.fake.FakeBooksRepository
import com.example.bookshell.ui.BooksUiState
import com.example.bookshell.ui.BooksViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

/**
 * BooksViewModel 的单元测试
 */
@OptIn(ExperimentalCoroutinesApi::class)
class BooksViewModelTest {

    @Test
    fun booksViewModel_searchBooks_verifyBooksUiStateSuccess() = runTest {
        val booksViewModel = BooksViewModel(
            booksRepository = FakeBooksRepository()
        )
        
        // 等待初始搜索完成
        booksViewModel.searchBooks("Android")
        
        // 验证状态是成功状态
        assertTrue(booksViewModel.uiState is BooksUiState.Success)
        
        // 验证返回的图书数量
        val successState = booksViewModel.uiState as BooksUiState.Success
        assertTrue(successState.books.isNotEmpty())
    }
    
    @Test
    fun booksViewModel_updateSearchQuery_verifyQueryUpdated() {
        val booksViewModel = BooksViewModel(
            booksRepository = FakeBooksRepository()
        )
        
        val testQuery = "Kotlin"
        booksViewModel.updateSearchQuery(testQuery)
        
        assertEquals(testQuery, booksViewModel.searchQuery)
    }
}