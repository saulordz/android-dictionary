package com.saulordz.dictionary.utils.extensions

import android.view.View
import android.widget.ViewFlipper

internal fun ViewFlipper.showView(view: View) {
  val index = indexOfChild(view)
  displayedChild = index
}