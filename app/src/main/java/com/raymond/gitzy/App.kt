package com.raymond.gitzy

import android.app.Application
import com.raymond.gitzy.di.apiModule
import com.raymond.gitzy.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    initDependencyInjection()
  }

  private fun initDependencyInjection() {
    startKoin {
      androidContext(this@App)
      modules(listOf(apiModule, viewModelModule))
    }
  }
}