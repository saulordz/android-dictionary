package com.saulordz.dictionary.ui.home.recycler.word

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.saulordz.dictionary.data.model.Word
import org.junit.Test

class WordDiffItemCallbackTest {
  private val callback = WordDiffItemCallback()

  @Test
  fun testAreItemsTheSameWithSameItem() {
    val testItem = Word(word = TEST_WORD_ONE)

    val actual = callback.areItemsTheSame(testItem, testItem)

    assertThat(actual).isTrue()
  }

  @Test
  fun testAreItemsTheSameWithDifferentItems() {
    val testItemOne = Word(word = TEST_WORD_ONE)
    val testItemTwo = Word(word = TEST_WORD_TWO)

    val actual = callback.areItemsTheSame(testItemOne, testItemTwo)

    assertThat(actual).isFalse()
  }

  @Test
  fun testAreContentsTheSameWithSameContents() {
    val testItem = Word(word = TEST_WORD_ONE)

    val actual = callback.areContentsTheSame(testItem, testItem)

    assertThat(actual).isTrue()
  }

  @Test
  fun testAreContentsTheSameWithDifferentContents() {
    val testItemOne = Word(word = TEST_WORD_ONE, phonetic = TEST_PHONETIC_ONE)
    val testItemTwo = Word(word = TEST_WORD_TWO, phonetic = TEST_PHONETIC_TWO)

    val actual = callback.areContentsTheSame(testItemOne, testItemTwo)

    assertThat(actual).isFalse()
  }

  private companion object {
    private const val TEST_WORD_ONE = "definicionuno"
    private const val TEST_WORD_TWO = "definiciondos"
    private const val TEST_PHONETIC_ONE = "ejemplosUno"
    private const val TEST_PHONETIC_TWO = "ejemplosDos"
  }
}