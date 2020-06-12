package com.saulordz.dictionary.ui.home.recycler.word

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.saulordz.dictionary.R
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.utils.extensions.inflateView

class WordAdapter :
  ListAdapter<Word, WordViewHolder>(WordDiffItemCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    WordViewHolder(parent.inflateView(R.layout.item_word))

  override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
    holder.setWord(getItem(position))
  }

}