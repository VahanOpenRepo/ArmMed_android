package com.armed.am.base

import android.app.Application
import com.armed.am.setup.network.di.NetworkModule.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ArmedApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin()=startKoin{
        androidLogger()
        androidContext(this@ArmedApplication)
        modules(listOf(networkModule, servicesModule, repositoriesModule, viewModelsModule, pagingSourcesModule))
    }

}