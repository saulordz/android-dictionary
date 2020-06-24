package com.saulordz.dictionary.utils.extensions

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class ObjectExtensionsTest {

  @Test
  fun testOrDefaultWithValue() {
    val testObject = TEST_STRING

    val actual = testObject.orDefault("Other")

    assertThat(actual).isEqualTo(TEST_STRING)
  }

  @Test
  fun testOrDefaultWithNull() {
    val testObject: String? = null

    val actual = testObject.orDefault(TEST_STRING)

    assertThat(actual).isEqualTo(TEST_STRING)
  }

  private companion object {
    private const val TEST_STRING = "Hello"
  }
}