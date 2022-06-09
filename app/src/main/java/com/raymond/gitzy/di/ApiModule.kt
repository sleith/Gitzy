package com.raymond.gitzy.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.raymond.gitzy.api.EventApi
import com.raymond.gitzy.api.UserApi
import com.raymond.gitzy.api.interactor.EventInteractor
import com.raymond.gitzy.api.interactor.UserInteractor
import com.raymond.gitzy.api.interceptor.ErrorInterceptor
import com.raymond.gitzy.api.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val apiModule = module {
  factory { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
  factory {
    GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .create()
  }

  single {
    OkHttpClient.Builder()
      .addNetworkInterceptor(get<HttpLoggingInterceptor>())
      .addInterceptor(get<HeaderInterceptor>())
      .addInterceptor(get<ErrorInterceptor>())
      .connectTimeout(NETWORK_TIMEOUT_DEFAULT_SECONDS, TimeUnit.SECONDS)
      .readTimeout(NETWORK_TIMEOUT_DEFAULT_SECONDS, TimeUnit.SECONDS)
      .build()
  }

  single { HeaderInterceptor() }
  single { ErrorInterceptor() }

  single {
    Retrofit.Builder()
      .addConverterFactory(ScalarsConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(get()))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(get())
      .baseUrl("https://api.github.com/")
      .build()
  }

  single { get<Retrofit>().create<UserApi>() }
  single { get<Retrofit>().create<EventApi>() }
  single { UserInteractor(get()) }
  single { EventInteractor(get()) }
}

private const val NETWORK_TIMEOUT_DEFAULT_SECONDS = 90L
