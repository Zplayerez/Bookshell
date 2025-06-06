package com.example.bookshell

import android.app.Application

/**
 * 自定义 Application 类
 */
class BookshellApplication : Application() {
    /**
     * 应用容器实例，用于依赖注入
     */
    lateinit var container: AppContainer
    
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
