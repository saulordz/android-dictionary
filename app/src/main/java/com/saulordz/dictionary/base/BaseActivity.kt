package com.saulordz.dictionary.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.saulordz.dictionary.di.Scopes
import com.saulordz.dictionary.utils.extensions.closeDrawer
import com.saulordz.dictionary.utils.extensions.isDrawerOpen
import kotlinx.android.synthetic.main.activity_home.*
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

  override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
    android.R.id.home -> {
      a_base_drawer.openDrawer(GravityCompat.START)
      true
    }
    else -> super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    if (isDrawerOpen) {
      closeDrawer()
    } else {
      super.onBackPressed()
    }
  }

  override fun onStop() {
    super.onStop()
    closeDrawer()
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