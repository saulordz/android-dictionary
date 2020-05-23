package com.saulordz.dictionary.base

import android.os.Bundle
import android.widget.Toast
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.saulordz.dictionary.di.Scopes
import toothpick.Scope
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieActivityModule


abstract class BaseActivity<V : MvpView, P : MvpPresenter<V>> :
  MvpActivity<V, P>(), MvpView {

  private lateinit var scope: Scope

  override fun onCreate(savedInstanceState: Bundle?) {
    initializeToothpick()
    super.onCreate(savedInstanceState)
  }

  override fun onDestroy() {
    Toothpick.closeScope(this)

    super.onDestroy()
  }

  abstract fun addModules(scope: Scope): Scope

  private fun initializeToothpick() {
    scope = Toothpick.openScopes(Scopes.AppScope::class.java, this)
    scope.installModules(SmoothieActivityModule(this))
    addModules(scope)
    Toothpick.inject(this, scope)
  }

  internal open fun showError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

}