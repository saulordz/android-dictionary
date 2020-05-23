package com.saulordz.dictionary.utils.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

internal fun ViewGroup.inflateView(@LayoutRes layoutId: Int) =
  LayoutInflater.from(context)
    .inflate(layoutId, this, false)