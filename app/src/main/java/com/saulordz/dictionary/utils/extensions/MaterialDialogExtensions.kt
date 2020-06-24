package com.saulordz.dictionary.utils.extensions

import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.getRecyclerView
import com.saulordz.dictionary.R

internal fun MaterialDialog.performPositiveClick() {
  findViewById<View>(R.id.md_button_positive).performClick()
}

internal fun MaterialDialog.removeItemAnimator(): MaterialDialog {
  getRecyclerView().itemAnimator = null
  return this
}