package com.saulordz.dictionary

import android.app.Application
import com.saulordz.dictionary.di.DataModule
import com.saulordz.dictionary.di.RxModule
import com.saulordz.dictionary.di.Scopes
import toothpick.Scope
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.configuration.ConfigurationHolder.configuration
import toothpick.smoothie.module.SmoothieApplicationModule

open class DictionaryApplication : Application() {

  internal open val modules
    get() = arrayOf(SmoothieApplicationModule(this), RxModule(), DataModule())

  lateinit var scope: Scope

  override fun onCreate() {
    super.onCreate()

    initializeToothpick()
  }

  internal open fun initializeToothpick() {
    Configuration.forDevelopment()
    Toothpick.setConfiguration(configuration)

    scope = Toothpick.openScope(Scopes.AppScope::class.java)
    scope.installModules(*modules)
    Toothpick.inject(this, scope)
  }
}