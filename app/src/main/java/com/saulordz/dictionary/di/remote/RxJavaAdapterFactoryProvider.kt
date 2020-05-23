package com.saulordz.dictionary.di.remote

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Inject
import javax.inject.Provider

class RxJavaAdapterFactoryProvider @Inject constructor() : Provider<RxJava2CallAdapterFactory> {

  override fun get(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

}