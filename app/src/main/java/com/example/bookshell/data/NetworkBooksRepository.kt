package com.example.bookshell.data

import com.example.bookshell.model.Book
import com.example.bookshell.network.BooksApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * 网络图书仓库实现类
 */
class NetworkBooksRepository(
    private val booksApiService: BooksApiService
) : BooksRepository {

    override suspend fun searchBooks(query: String): List<Book> {
        return try {
            // 首先搜索图书列表
            val searchResponse = booksApiService.searchBooks(query)
            val bookItems = searchResponse.items ?: emptyList()
            
            // 并发获取每本图书的详细信息（包含缩略图）
            coroutineScope {
                bookItems.map { bookItem ->
                    async {
                        try {
                            val detailedBook = booksApiService.getBookById(bookItem.id)
                            val thumbnailUrl = detailedBook.volumeInfo?.imageLinks?.thumbnail
                                ?.replace("http://", "https://") // 确保使用HTTPS
                                ?: ""
                            
                            if (thumbnailUrl.isNotEmpty()) {
                                Book.fromBookItem(detailedBook, thumbnailUrl)
                            } else {
                                null
                            }
                        } catch (e: Exception) {
                            // 如果获取特定图书失败，跳过该图书
                            null
                        }
                    }
                }.awaitAll().filterNotNull() // 过滤掉null值
            }
        } catch (e: Exception) {
            // 如果搜索失败，返回空列表
            emptyList()
        }
    }
}
