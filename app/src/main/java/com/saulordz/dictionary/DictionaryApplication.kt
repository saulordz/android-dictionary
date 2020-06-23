package com.saulordz.dictionary

import android.app.Application
import com.saulordz.dictionary.data.model.SharedPrefManager
import com.saulordz.dictionary.di.DataModule
import com.saulordz.dictionary.di.RxModule
import com.saulordz.dictionary.di.Scopes
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.smoothie.module.SmoothieApplicationModule

open class DictionaryApplication : Application() {

  internal open val modules
    get() = arrayOf(SmoothieApplicationModule(this), RxModule(), DataModule())

  lateinit var scope: Scope

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    initializeToothpick()
    initializeObjectPreference()
  }

  private fun initializeToothpick() {
    val configuration = if (BuildConfig.DEBUG) {
      Configuration.forDevelopment()
    } else {
      Configuration.forProduction()
    }
    Toothpick.setConfiguration(configuration)

    scope = Toothpick.openScope(Scopes.AppScope::class.java)
    scope.installModules(*modules)
    Toothpick.inject(this, scope)
  }

  private fun initializeObjectPreference() = SharedPrefManager.initialize(this)
}