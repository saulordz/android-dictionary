package com.saulordz.dictionary.utils.extensions

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

internal fun RecyclerView.addDefaultVerticalSpacing() =
  addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))