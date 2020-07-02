package com.saulordz.dictionary.utils.extensions

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.Test

class BooleanExtensionsTest {

  @Test
  fun testOrFalseWithNull() {
    val value: Boolean? = null

    val actual = value.orFalse()

    assertThat(actual).isFalse()
  }

  @Test
  fun testOrFalseWithTrue() {
    val value: Boolean = true

    val actual = value.orFalse()

    assertThat(actual).isTrue()
  }

  @Test
  fun testOrTrueWithNull() {
    val value: Boolean? = null

    val actual = value.orTrue()

    assertThat(actual).isTrue()
  }

  @Test
  fun testOrTrueWithFalse() {
    val value: Boolean? = false

    val actual = value.orTrue()

    assertThat(actual).isFalse()
  }
}