package com.saulordz.dictionary.ui.home.dialog

import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
import org.junit.Test

class LanguageDiffItemCallbackTest {

  private val callback = LanguageDiffItemCallback()

  @Test
  fun testAreItemsTheSameWithSameItem() {
    val testItem = LanguageSelectionState(Language.SPANISH, true)

    val actual = callback.areItemsTheSame(testItem, testItem)

    assertk.assertThat(actual).isTrue()
  }

  @Test
  fun testAreItemsTheSameWithDifferentItems() {
    val testItemOne = LanguageSelectionState(Language.SPANISH, true)
    val testItemTwo = LanguageSelectionState(Language.SPANISH, false)

    val actual = callback.areItemsTheSame(testItemOne, testItemTwo)

    assertk.assertThat(actual).isFalse()
  }

  @Test
  fun testAreContentsTheSameWithSameContents() {
    val testItem = LanguageSelectionState(Language.SPANISH, true)

    val actual = callback.areContentsTheSame(testItem, testItem)

    assertk.assertThat(actual).isTrue()
  }

  @Test
  fun testAreContentsTheSameWithDifferentContents() {
    val testItemOne = LanguageSelectionState(Language.SPANISH, true)
    val testItemTwo = LanguageSelectionState(Language.ENGLISH, false)

    val actual = callback.areContentsTheSame(testItemOne, testItemTwo)

    assertk.assertThat(actual).isFalse()
  }
}