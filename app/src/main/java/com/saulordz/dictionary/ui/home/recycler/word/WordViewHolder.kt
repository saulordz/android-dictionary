package com.saulordz.dictionary.ui.home.recycler.word

import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.ui.home.recycler.definition.DefinitionAdapter
import com.saulordz.dictionary.utils.extensions.setTextAndVisibility
import kotlinx.android.synthetic.main.item_word.view.*

class WordViewHolder(
  view: View
) : RecyclerView.ViewHolder(view) {

  @VisibleForTesting val definitionAdapter by lazy { DefinitionAdapter() }

  init {
    with(itemView) {
      i_word_recycler.adapter = definitionAdapter
    }
  }

  fun setWord(word: Word) = with(itemView) {
    i_word_text.setTextAndVisibility(word.formattedWord)
    definitionAdapter.submitList(word.definitions)
  }
}