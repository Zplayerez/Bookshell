package com.example.bookshell.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshell.ui.BooksUiState
import com.example.bookshell.ui.BooksViewModel
import com.example.bookshell.ui.components.BookCard
import com.example.bookshell.ui.components.SearchBar

/**
 * 图书主屏幕
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(
    booksViewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // 顶部应用栏
        TopAppBar(
            title = { 
                Text(
                    text = "书架",
                    fontWeight = FontWeight.Bold
                ) 
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
        
        // 搜索栏
        SearchBar(
            query = booksViewModel.searchQuery,
            onQueryChange = booksViewModel::updateSearchQuery,
            onSearch = { booksViewModel.searchBooks() }
        )
        
        // 内容区域
        when (val uiState = booksViewModel.uiState) {
            is BooksUiState.Loading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }
            is BooksUiState.Success -> {
                BooksGridScreen(
                    books = uiState.books,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is BooksUiState.Error -> {
                ErrorScreen(
                    onRetry = { booksViewModel.searchBooks() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

/**
 * 加载中屏幕
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "正在加载图书...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * 错误屏幕
 */
@Composable
fun ErrorScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "😔",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "加载失败",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "请检查网络连接后重试",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("重试")
            }
        }
    }
}

/**
 * 图书网格屏幕
 */
@Composable
fun BooksGridScreen(
    books: List<com.example.bookshell.model.Book>,
    modifier: Modifier = Modifier
) {
    if (books.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📚",
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "没有找到图书",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "尝试搜索其他关键词",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            items(books) { book ->
                BookCard(book = book)
            }
        }
    }
}
