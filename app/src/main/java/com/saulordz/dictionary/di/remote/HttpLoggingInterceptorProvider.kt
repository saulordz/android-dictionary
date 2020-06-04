package com.saulordz.dictionary.di.remote

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class HttpLoggingInterceptorProvider @Inject constructor() : Provider<HttpLoggingInterceptor> {

  override fun get(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor(defaultLogger)
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
  }

  private val defaultLogger by lazy {
    object : HttpLoggingInterceptor.Logger {
      override fun log(message: String) {
        Timber.d(message)
      }
    }
  }
}