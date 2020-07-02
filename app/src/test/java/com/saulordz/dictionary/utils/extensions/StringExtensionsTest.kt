package com.saulordz.dictionary.utils.extensions

import assertk.assertions.isEqualTo
import org.junit.Test

class StringExtensionsTest {

  @Test
  fun testStripSymbols() {
    val originalString = "!Symb@l!!?.$!&"
    val expectedString = "Symbl"

    val actual = originalString.stripSymbols()

    assertk.assertThat(actual).isEqualTo(expectedString)
  }
}