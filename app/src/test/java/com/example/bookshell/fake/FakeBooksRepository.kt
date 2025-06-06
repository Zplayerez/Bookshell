package com.example.bookshell.fake

import com.example.bookshell.data.BooksRepository
import com.example.bookshell.model.Book

/**
 * 用于测试的假图书仓库实现
 */
class FakeBooksRepository : BooksRepository {
    
    override suspend fun searchBooks(query: String): List<Book> {
        return fakeBooks.filter { 
            it.title.contains(query, ignoreCase = true) || 
            it.authors.contains(query, ignoreCase = true) 
        }
    }
}

private val fakeBooks = listOf(
    Book(
        id = "1",
        title = "Android开发艺术探索",
        authors = "任玉刚",
        thumbnailUrl = "https://picsum.photos/200/300?random=1"
    ),
    Book(
        id = "2", 
        title = "Kotlin实战",
        authors = "德米特里·热梅罗夫, 斯维特拉娜·伊萨科娃",
        thumbnailUrl = "https://picsum.photos/200/300?random=2"
    ),
    Book(
        id = "3",
        title = "深入理解Android",
        authors = "邓凡平",
        thumbnailUrl = "https://picsum.photos/200/300?random=3"
    ),
    Book(
        id = "4",
        title = "Android进阶之光",
        authors = "刘望舒", 
        thumbnailUrl = "https://picsum.photos/200/300?random=4"
    ),
    Book(
        id = "5",
        title = "Jetpack Compose权威指南",
        authors = "张鸿洋",
        thumbnailUrl = "https://picsum.photos/200/300?random=5"
    )
)
