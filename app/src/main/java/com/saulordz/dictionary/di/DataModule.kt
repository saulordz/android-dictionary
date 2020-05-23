package com.saulordz.dictionary.di

import com.saulordz.dictionary.data.remote.GoogleDictionaryService
import com.saulordz.dictionary.di.remote.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import toothpick.config.Module

class DataModule : Module() {
  init {
    //base networking
    bind(HttpLoggingInterceptor::class.java).toProvider(HttpLoggingInterceptorProvider::class.java)
    bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java)
    bind(Converter.Factory::class.java).toProvider(MoshiConverterFactoryProvider::class.java)
    bind(CallAdapter.Factory::class.java).toProvider(RxJavaAdapterFactoryProvider::class.java)
    bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java)

    //retrofit services
    bind(GoogleDictionaryService::class.java).toProvider(GoogleDictionaryServiceProvider::class.java)
  }
}