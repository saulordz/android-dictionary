package com.saulordz.dictionary.ui.home.recycler.recentsearch

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.saulordz.dictionary.data.model.RecentSearch
import org.junit.Test

class RecentSearchDiffItemCallbackTest {

  private val callback = RecentSearchDiffItemCallback()

  @Test
  fun testAreItemsTheSameWithSame() {
    val item1 = RecentSearch()

    val actual = callback.areItemsTheSame(item1, item1)

    assertThat(actual).isTrue()
  }


  @Test
  fun testAreItemsTheSameWithDifferent() {
    val item1 = RecentSearch("x12")
    val item2 = RecentSearch("x13")

    val actual = callback.areItemsTheSame(item1, item2)

    assertThat(actual).isFalse()
  }

  @Test
  fun testAreContentsTheSameWithSame() {
    val item1 = RecentSearch("x12")

    val actual = callback.areContentsTheSame(item1, item1)

    assertThat(actual).isTrue()
  }

  @Test
  fun testAreContentsTheSameWithDifferent() {
    val item1 = RecentSearch("x12")
    val item2 = RecentSearch("x13")

    val actual = callback.areContentsTheSame(item1, item2)

    assertThat(actual).isFalse()
  }
}