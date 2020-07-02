package com.saulordz.dictionary.utils.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager

internal fun View.makeVisible() {
  visibility = VISIBLE
}

internal fun View.makeGone() {
  visibility = GONE
}

internal fun View.hideInputKeyboard(inputMethodManager: InputMethodManager) {
  inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}