package com.saulordz.dictionary.ui.home.recycler

import androidx.recyclerview.widget.DiffUtil
import com.saulordz.dictionary.data.model.Definition

class DefinitionDiffItemCallback : DiffUtil.ItemCallback<Definition>() {

  override fun areItemsTheSame(oldItem: Definition, newItem: Definition) =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: Definition, newItem: Definition) =
    oldItem.definition == newItem.definition &&
        oldItem.examples == newItem.examples &&
        oldItem.synonyms == newItem.synonyms

}