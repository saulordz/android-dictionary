package com.saulordz.dictionary.utils.extensions

import android.widget.TextView
import androidx.annotation.StringRes

internal fun TextView.getStringText() = text.toString()

internal fun TextView.setTextAndVisibility(newText: String?) {
  if (newText != null) {
    text = newText
    makeVisible()
  } else {
    makeGone()
  }
}