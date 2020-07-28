package com.saulordz.dictionary.ui.home.recycler.recentsearch

import android.view.LayoutInflater
import android.widget.FrameLayout
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.RecentSearch
import com.saulordz.dictionary.testUtils.assertions.hasText
import kotlinx.android.synthetic.main.item_recent_search.view.*
import org.junit.Test

class RecentSearchViewHolderTest : BaseActivityTest() {

  private val mockRecentSearch = mock<RecentSearch> {
    on { language } doReturn Language.SPANISH
    on { searchTerm } doReturn TEST_SEARCH_TERM
  }

  private val itemView = LayoutInflater.from(application).inflate(R.layout.item_recent_search, FrameLayout(application), false)

  private val viewHolder = RecentSearchViewHolder(itemView) {}

  @Test
  fun testSetRecentSearch() {
    itemView.i_recent_search_text.text = ""
    itemView.i_recent_search_language.text = ""

    viewHolder.recentSearch = mockRecentSearch

    assertThat(itemView.i_recent_search_text).hasText(TEST_SEARCH_TERM.capitalize())
    assertThat(itemView.i_recent_search_language).hasText(R.string.message_language_name, Language.SPANISH.languageStringRes)
  }

  @Test
  fun testGetRecentSearch() {
    viewHolder.recentSearch = mockRecentSearch

    val actual = viewHolder.recentSearch

    assertThat(actual).isEqualTo(mockRecentSearch)
  }

  @Test
  fun testOnRecentSearchClicked() {
    var wasCalled = false
    RecentSearchViewHolder(itemView) { wasCalled = true }

    itemView.i_recent_search.performClick()

    assertThat(wasCalled).isTrue()
  }

  private companion object {
    private const val TEST_SEARCH_TERM = "testsrchtmr"
  }
}