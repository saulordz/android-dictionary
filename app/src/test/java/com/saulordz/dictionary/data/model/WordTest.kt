package com.saulordz.dictionary.data.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class WordTest {

  @Test
  fun testGetFormattedWord() {
    val originalString = "!hello!"
    val expectedString = "Hello"
    val word = Word(word = originalString)

    val actual = word.formattedWord

    assertThat(actual).isEqualTo(expectedString)
  }
}