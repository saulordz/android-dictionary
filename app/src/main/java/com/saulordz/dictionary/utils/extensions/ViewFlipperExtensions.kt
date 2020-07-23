package com.saulordz.dictionary.utils.extensions

import android.view.View
import android.widget.ViewFlipper

internal fun ViewFlipper.showView(view: View) {
  val index = indexOfChild(view)
  if (index == -1) {
    addView(view)
  }
  if (index != displayedChild) {
    displayedChild = index
  }
}

internal fun ViewFlipper.isDisplayed(view: View) = indexOfChild(view) == displayedChild