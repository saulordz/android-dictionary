package com.saulordz.dictionary.ui.home.recycler.definition

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.saulordz.dictionary.R
import com.saulordz.dictionary.data.model.Definition
import com.saulordz.dictionary.utils.extensions.inflateView

class DefinitionAdapter :
  ListAdapter<Definition, DefinitionViewHolder>(DefinitionDiffItemCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    DefinitionViewHolder(parent.inflateView(R.layout.item_definition))

  override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) =
    holder.setDefinition(getItem(position))

}