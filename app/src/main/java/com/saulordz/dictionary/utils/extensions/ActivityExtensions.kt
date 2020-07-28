package com.saulordz.dictionary.utils.extensions

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.saulordz.dictionary.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_main_navigation_drawer.*
import kotlinx.android.synthetic.main.include_main_toolbar.*


internal val AppCompatActivity.isDrawerOpen
  get() = a_base_drawer?.isDrawerOpen(GravityCompat.START).orFalse()

internal fun AppCompatActivity.hideInputKeyboard(inputMethodManager: InputMethodManager) =
  window.decorView.rootView.hideInputKeyboard(inputMethodManager)

internal fun AppCompatActivity.createBackToolbar() {
  setSupportActionBar(i_main_toolbar)

  supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
  supportActionBar?.setDisplayHomeAsUpEnabled(true)
}

internal fun AppCompatActivity.createDrawerToolbar() {
  setSupportActionBar(i_main_toolbar)

  val drawerToggle = ActionBarDrawerToggle(this, a_base_drawer, R.string.message_toolbar_open, R.string.message_toolbar_close)
  a_base_drawer.addDrawerListener(drawerToggle)
  drawerToggle.syncState()

  supportActionBar?.setDisplayHomeAsUpEnabled(true)

  i_main_navigation.bringToFront()
}

internal fun AppCompatActivity.closeDrawer() {
  a_base_drawer?.closeDrawer(GravityCompat.START)
}

internal fun AppCompatActivity.openDrawer() {
  a_base_drawer?.openDrawer(GravityCompat.START)
}