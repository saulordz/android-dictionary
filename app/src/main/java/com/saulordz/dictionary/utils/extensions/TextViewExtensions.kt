package com.saulordz.dictionary.utils.extensions

import android.widget.TextView
import androidx.annotation.StringRes

internal fun TextView.getStringText() = text.toString()

internal fun TextView.setTextAndVisibility(newText: String?) = if (newText != null && newText.isNotBlank()) {
  text = newText
  makeVisible()
} else {
  makeGone()
}

internal fun TextView.setTextAndVisibility(@StringRes stringResId: Int?, vararg formatArgs: Any?) =
  if (stringResId != null && stringResId != 0 && formatArgs.all { it != null }) {
    setTextAndVisibility(context.getString(stringResId, *formatArgs))
  } else {
    makeGone()
  }

internal fun TextView.setTextAndVisibility(@StringRes stringResId: Int?) =
  if (stringResId != null && stringResId != 0) {
    setTextAndVisibility(context.getString(stringResId))
  } else {
    makeGone()
  }
