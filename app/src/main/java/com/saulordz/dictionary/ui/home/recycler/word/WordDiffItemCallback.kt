package com.saulordz.dictionary.ui.home.recycler.word

import androidx.recyclerview.widget.DiffUtil
import com.saulordz.dictionary.data.model.Word

class WordDiffItemCallback : DiffUtil.ItemCallback<Word>() {

  override fun areItemsTheSame(oldItem: Word, newItem: Word) =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: Word, newItem: Word) =
    oldItem.word == newItem.word &&
        oldItem.phonetic == newItem.phonetic &&
        oldItem.origin == newItem.origin &&
        oldItem.meanings == newItem.meanings

}