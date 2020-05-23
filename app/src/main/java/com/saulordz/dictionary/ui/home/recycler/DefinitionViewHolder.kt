package com.saulordz.dictionary.ui.home.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.saulordz.dictionary.data.model.Definition
import kotlinx.android.synthetic.main.item_definition.view.*

class DefinitionViewHolder(
  view: View
) : RecyclerView.ViewHolder(view) {

  fun setDefinition(definition: Definition) = with(itemView) {
    i_definition_text.text = definition.definition
  }
}