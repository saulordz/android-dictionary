package com.saulordz.dictionary.base

import android.content.Context
import android.os.LocaleList
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.saulordz.dictionary.di.Scopes
import com.saulordz.dictionary.utils.extensions.closeDrawer
import com.saulordz.dictionary.utils.extensions.isDrawerOpen
import kotlinx.android.synthetic.main.activity_home.*
import toothpick.Scope
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieActivityModule


abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> :
  MvpActivity<V, P>(), BaseContract.View {

  private lateinit var scope: Scope

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

  override fun attachBaseContext(newBase: Context?) {
    initializeToothpick()
    val customContext = createCustomContext(newBase)
    super.attachBaseContext(customContext)
  }

  override fun recreateView() = recreate()

  abstract fun addModules(scope: Scope): Scope

  private fun initializeToothpick() {
    scope = Toothpick.openScopes(Scopes.AppScope::class.java, this)
    scope.installModules(SmoothieActivityModule(this))
    addModules(scope)
    Toothpick.inject(this, scope)
  }

  private fun createCustomContext(context: Context?): Context? {
    val customContext = createPresenter().createCustomContext(context)
    val customLocales = customContext?.resources?.configuration?.locales
    customLocales?.let { LocaleList.setDefault(it) }
    return customContext
  }

  internal open fun showError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

}