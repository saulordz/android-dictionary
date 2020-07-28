package com.saulordz.dictionary.ui.home.recycler.recentsearch

import androidx.recyclerview.widget.DiffUtil
import com.saulordz.dictionary.data.model.RecentSearch

class RecentSearchDiffItemCallback : DiffUtil.ItemCallback<RecentSearch>() {

  override fun areItemsTheSame(oldItem: RecentSearch, newItem: RecentSearch) =
    oldItem == newItem

  override fun areContentsTheSame(oldItem: RecentSearch, newItem: RecentSearch) =
    oldItem.searchTerm == newItem.searchTerm &&
        oldItem.language == newItem.language &&
        oldItem.words == newItem.words

}