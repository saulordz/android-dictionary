package com.saulordz.dictionary.di.remote

import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Provider

class RetrofitProvider @Inject constructor(
  private val okHttpClient: OkHttpClient,
  private val converterFactory: Converter.Factory,
  private val callAdapterFactory: CallAdapter.Factory
) : Provider<Retrofit> {

  override fun get(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(converterFactory)
      .addCallAdapterFactory(callAdapterFactory)
      .build()
  }

  private companion object {
    private const val BASE_URL = "https://api.dictionaryapi.dev"
  }
}