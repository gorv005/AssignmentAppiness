package com.appiness.assignmentappiness.base

import android.app.Application
import com.appiness.assignmentappiness.modules.NetworkModule
import com.appstreet.top_github.modules.ViewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import io.reactivex.plugins.RxJavaPlugins


class BaseApplication : Application() {

    override fun onCreate() {

        super.onCreate()
        sInstance = this

        startKoin {
            androidContext(this@BaseApplication)
            logger(AndroidLogger())
            modules(listOf(NetworkModule.networkModule, ViewModelModules.viewModules))
            RxJavaPlugins.setErrorHandler{}

        }
    }

    companion object {

        private lateinit var sInstance: BaseApplication

        fun getInstance() = sInstance
    }
}