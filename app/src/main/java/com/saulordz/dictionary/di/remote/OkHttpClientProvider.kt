package com.saulordz.dictionary.di.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Provider


class OkHttpClientProvider @Inject constructor(
  private val loggingInterceptor: HttpLoggingInterceptor
) : Provider<OkHttpClient> {

  override fun get(): OkHttpClient =
    OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .build()
}