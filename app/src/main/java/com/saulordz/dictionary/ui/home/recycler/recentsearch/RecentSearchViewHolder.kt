package com.saulordz.dictionary.ui.home.recycler.recentsearch

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.saulordz.dictionary.R
import com.saulordz.dictionary.data.model.RecentSearch
import com.saulordz.dictionary.utils.extensions.setTextAndVisibility
import kotlinx.android.synthetic.main.item_recent_search.view.*

class RecentSearchViewHolder(
  view: View,
  onRecentSearchClicked: OnRecentSearchClicked
) : RecyclerView.ViewHolder(view) {

  var recentSearch: RecentSearch? = null
    set(value) = with(itemView) {
      field = value
      val languageString = value?.language?.languageStringRes?.let { context.getString(it) }
      i_recent_search_text.setTextAndVisibility(value?.searchTerm?.capitalize())
      i_recent_search_language.setTextAndVisibility(R.string.message_language_name, languageString)
    }

  init {
    itemView.i_recent_search.setOnClickListener {
      onRecentSearchClicked(recentSearch)
    }
  }
}
