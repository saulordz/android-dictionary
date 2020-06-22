package com.saulordz.dictionary.utils.extensions

import android.view.View
import android.widget.TextView
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import org.junit.Test

class TextViewExtensionsTest : BaseActivityTest() {

  private val textView = TextView(application)

  @Test
  fun testSetTextAndVisibilityWithNullString() {
    val nullString: String? = null
    textView.makeVisible()
    textView.text = TEST_STRING

    textView.setTextAndVisibility(nullString)

    assertThat(textView.text).isEqualTo(TEST_STRING)
    assertThat(textView.visibility).isEqualTo(View.GONE)
  }

  @Test
  fun testSetTextAndVisibilityWithString() {
    textView.makeGone()
    textView.text = null

    textView.setTextAndVisibility(TEST_STRING)

    assertThat(textView.text).isEqualTo(TEST_STRING)
    assertThat(textView.visibility).isEqualTo(View.VISIBLE)
  }

  @Test
  fun testSetTextAndVisibilityWithResourceId() {
    textView.makeGone()
    textView.text = null

    textView.setTextAndVisibility(R.string.app_name)

    assertThat(textView.text).isEqualTo(application.getString(R.string.app_name))
    assertThat(textView.visibility).isEqualTo(View.VISIBLE)
  }

  @Test
  fun testSetTextAndVisibilityWithNullResourceId() {
    val nullResourceId: Int? = null
    textView.makeVisible()
    textView.text = TEST_STRING

    textView.setTextAndVisibility(nullResourceId)

    assertThat(textView.text).isEqualTo(TEST_STRING)
    assertThat(textView.visibility).isEqualTo(View.GONE)
  }

  @Test
  fun testSetTextAndVisibilityWithZeroResourceId() {
    val nullResourceId = 0
    textView.makeVisible()
    textView.text = TEST_STRING

    textView.setTextAndVisibility(nullResourceId)

    assertThat(textView.text).isEqualTo(TEST_STRING)
    assertThat(textView.visibility).isEqualTo(View.GONE)
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
  }
}