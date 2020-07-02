package com.saulordz.dictionary.ui.home.dialog

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.saulordz.dictionary.R
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.utils.extensions.inflateView

class LanguageDialogAdapter(
  private val onLanguageClickedListener: OnLanguageClickedListener
) : ListAdapter<LanguageSelectionState, LanguageViewHolder>(LanguageDiffItemCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    LanguageViewHolder(parent.inflateView(R.layout.item_language), onLanguageClickedListener)

  override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
    holder.languageSelectionState = getItem(position)
  }

}