package com.example.bookshell.model

import com.google.gson.annotations.SerializedName

/**
 * 图书搜索响应的数据类
 */
data class BooksResponse(
    val items: List<BookItem>? = null
)

/**
 * 单个图书项目的数据类
 */
data class BookItem(
    val id: String,
    @SerializedName("volumeInfo") 
    val volumeInfo: VolumeInfo? = null
)

/**
 * 图书详细信息的数据类
 */
data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val description: String? = null,
    @SerializedName("imageLinks") 
    val imageLinks: ImageLinks? = null
)

/**
 * 图片链接信息的数据类
 */
data class ImageLinks(
    val thumbnail: String? = null,
    val smallThumbnail: String? = null
)

/**
 * 用于显示的简化图书数据类
 */
data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val thumbnailUrl: String
) {
    companion object {
        /**
         * 从 BookItem 创建 Book 对象
         */
        fun fromBookItem(bookItem: BookItem, thumbnailUrl: String): Book {
            return Book(
                id = bookItem.id,
                title = bookItem.volumeInfo?.title ?: "未知标题",
                authors = bookItem.volumeInfo?.authors?.joinToString(", ") ?: "未知作者",
                thumbnailUrl = thumbnailUrl
            )
        }
    }
}
