package com.saulordz.dictionary.di.remote

import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class MoshiConverterFactoryProvider @Inject constructor() : Provider<Converter.Factory> {

  override fun get(): MoshiConverterFactory = MoshiConverterFactory.create()

}