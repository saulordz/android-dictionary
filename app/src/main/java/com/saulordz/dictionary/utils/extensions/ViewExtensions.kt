package com.saulordz.dictionary.utils.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

internal fun View.makeVisible() {
  visibility = VISIBLE
}

internal fun View.makeGone() {
  visibility = GONE
}