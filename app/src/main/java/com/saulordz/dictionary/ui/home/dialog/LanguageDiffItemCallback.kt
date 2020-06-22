package com.saulordz.dictionary.ui.home.dialog

import androidx.recyclerview.widget.DiffUtil
import com.saulordz.dictionary.data.model.LanguageSelectionState

class LanguageDiffItemCallback : DiffUtil.ItemCallback<LanguageSelectionState>() {

  override fun areItemsTheSame(oldItem: LanguageSelectionState, newItem: LanguageSelectionState) =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: LanguageSelectionState, newItem: LanguageSelectionState) =
    oldItem.selected == newItem.selected &&
        oldItem.language == newItem.language

}