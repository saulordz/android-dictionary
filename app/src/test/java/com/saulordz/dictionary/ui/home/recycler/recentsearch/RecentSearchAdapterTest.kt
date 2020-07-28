package com.saulordz.dictionary.ui.home.recycler.recentsearch

import android.widget.FrameLayout
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.RecentSearch
import org.junit.Test

class RecentSearchAdapterTest : BaseActivityTest() {

  private val mockRecentSearch = mock<RecentSearch>()
  private val mockViewHolder = mock<RecentSearchViewHolder>()

  private val adapter = RecentSearchAdapter {}

  @Test
  fun testOnCreateViewHolder() {
    val holder = adapter.onCreateViewHolder(FrameLayout(application), 0)

    assertThat(holder).isInstanceOf(RecentSearchViewHolder::class)
  }

  @Test
  fun testOnBindViewHolder() {
    adapter.submitList(listOf(mockRecentSearch))

    adapter.onBindViewHolder(mockViewHolder, 0)

    verify(mockViewHolder).recentSearch = mockRecentSearch
  }
}