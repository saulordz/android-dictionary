package com.saulordz.dictionary.ui.home.recycler.recentsearch

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.saulordz.dictionary.R
import com.saulordz.dictionary.data.model.RecentSearch
import com.saulordz.dictionary.utils.extensions.inflateView

class RecentSearchAdapter(
  private val onRecentSearchClicked: OnRecentSearchClicked
) : ListAdapter<RecentSearch, RecentSearchViewHolder>(RecentSearchDiffItemCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    RecentSearchViewHolder(parent.inflateView(R.layout.item_recent_search), onRecentSearchClicked)

  override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
    holder.recentSearch = getItem(position)
  }

}