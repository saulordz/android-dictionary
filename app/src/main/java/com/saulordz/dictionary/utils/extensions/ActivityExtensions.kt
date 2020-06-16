package com.saulordz.dictionary.utils.extensions

import android.app.Activity
import android.view.inputmethod.InputMethodManager


internal fun Activity.hideInputKeyboard(inputMethodManager: InputMethodManager) {
  window.decorView.rootView.hideInputKeyboard(inputMethodManager)
}