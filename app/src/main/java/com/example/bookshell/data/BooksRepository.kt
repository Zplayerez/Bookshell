package com.example.bookshell.data

import com.example.bookshell.model.Book

/**
 * 图书仓库接口，定义数据访问方法
 */
interface BooksRepository {
    /**
     * 搜索图书并返回包含缩略图的图书列表
     * @param query 搜索关键词
     * @return 图书列表
     */
    suspend fun searchBooks(query: String): List<Book>
}
