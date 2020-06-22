package com.saulordz.dictionary.ui.home.dialog

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.utils.extensions.orFalse
import com.saulordz.dictionary.utils.extensions.setTextAndVisibility
import kotlinx.android.synthetic.main.item_language.view.*

class LanguageViewHolder(
  view: View,
  private val onLanguageClickedListener: OnLanguageClickedListener
) : RecyclerView.ViewHolder(view) {

  var languageSelectionState: LanguageSelectionState? = null
    set(value) = with(itemView) {
      field = value
      i_language_radio.setTextAndVisibility(value?.language?.languageStringRes)
      i_language_radio.isChecked = languageSelectionState?.selected.orFalse()
    }

  init {
    itemView.i_language_radio.setOnClickListener {
      onLanguageClickedListener(languageSelectionState)
    }
  }
}