package com.saulordz.dictionary.di

import javax.inject.Scope

object Scopes {
  @Scope
  @Target(AnnotationTarget.TYPE)
  @Retention(AnnotationRetention.RUNTIME)
  annotation class AppScope
}