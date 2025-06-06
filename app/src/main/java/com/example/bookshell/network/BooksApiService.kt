package com.example.bookshell.network

import com.example.bookshell.model.BookItem
import com.example.bookshell.model.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Google Books API 服务接口
 */
interface BooksApiService {
    
    /**
     * 搜索图书
     * @param query 搜索关键词
     * @param maxResults 最大结果数，默认为40
     */
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 40
    ): BooksResponse
    
    /**
     * 根据ID获取特定图书的详细信息
     * @param volumeId 图书的ID
     */
    @GET("volumes/{id}")
    suspend fun getBookById(@Path("id") volumeId: String): BookItem
}
