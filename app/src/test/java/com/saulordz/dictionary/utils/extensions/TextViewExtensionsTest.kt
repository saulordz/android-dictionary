package com.saulordz.dictionary.utils.extensions

import android.view.View
import android.widget.TextView
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.testUtils.assertions.hasText
import com.saulordz.dictionary.testUtils.assertions.isGone
import com.saulordz.dictionary.testUtils.assertions.isVisible
import org.junit.Test

class TextViewExtensionsTest : BaseActivityTest() {

  private val textView = TextView(application)

  @Test
  fun testSetTextAndVisibilityWithNullString() {
    val nullString: String? = null
    textView.makeVisible()
    textView.text = TEST_STRING

    textView.setTextAndVisibility(nullString)

    assertThat(textView).hasText(TEST_STRING)
    assertThat(textView).isGone()
  }

  @Test
  fun testSetTextAndVisibilityWithString() {
    textView.makeGone()
    textView.text = null

    textView.setTextAndVisibility(TEST_STRING)

    assertThat(textView).hasText(TEST_STRING)
    assertThat(textView).isVisible()
  }

  @Test
  fun testSetTextAndVisibilityWithResourceId() {
    textView.makeGone()
    textView.text = null

    textView.setTextAndVisibility(R.string.app_name)

    assertThat(textView).hasText(R.string.app_name)
    assertThat(textView).isVisible()
  }

  @Test
  fun testSetTextAndVisibilityWithNullResourceId() {
    val nullResourceId: Int? = null
    textView.makeVisible()
    textView.text = TEST_STRING

    textView.setTextAndVisibility(nullResourceId)

    assertThat(textView).hasText(TEST_STRING)
    assertThat(textView).isGone()
  }

  @Test
  fun testSetTextAndVisibilityWithZeroResourceId() {
    val nullResourceId = 0
    textView.makeVisible()
    textView.text = TEST_STRING

    textView.setTextAndVisibility(nullResourceId)

    assertThat(textView).hasText(TEST_STRING)
    assertThat(textView).isGone()
  }

  @Test
  fun testSetTextAndVisibilityWithValidFormatArg() {
    textView.makeVisible()
    textView.text = ""

    textView.setTextAndVisibility(R.string.message_version, TEST_FORMAT_ARG)

    assertThat(textView).hasText(R.string.message_version, TEST_FORMAT_ARG)
    assertThat(textView).isVisible()
  }

  @Test
  fun testSetTextAndVisibilityWithNullFormatArg() {
    val nullVarArg = null
    textView.makeVisible()
    textView.text = TEST_STRING

    textView.setTextAndVisibility(R.string.app_name, nullVarArg, "other arg")

    assertThat(textView).hasText(TEST_STRING)
    assertThat(textView).isGone()
  }

  @Test
  fun testGetStringText() {
    textView.text = TEST_STRING

    val actual = textView.getStringText()

    assertThat(actual).isInstanceOf(String::class)
    assertThat(actual).isEqualTo(TEST_STRING)
  }

  private companion object {
    private const val TEST_STRING = "stringtst"
    private const val TEST_FORMAT_ARG = "imaformat"
  }
}