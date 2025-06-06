package com.example.bookshell

import com.example.bookshell.data.BooksRepository
import com.example.bookshell.data.NetworkBooksRepository
import com.example.bookshell.network.BooksApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 应用容器，提供依赖注入
 */
interface AppContainer {
    val booksRepository: BooksRepository
}

/**
 * 默认应用容器实现
 */
class DefaultAppContainer : AppContainer {
    
    private val baseUrl = "https://www.googleapis.com/books/v1/"
    
    /**
     * 创建 OkHttpClient，添加日志拦截器
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    
    /**
     * 使用 Retrofit 创建 API 服务
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
    
    /**
     * 创建 BooksApiService 实例
     */
    private val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
    
    /**
     * 创建 BooksRepository 实例
     */
    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(retrofitService)
    }
}
