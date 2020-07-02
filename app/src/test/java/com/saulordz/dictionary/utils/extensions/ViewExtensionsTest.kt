package com.saulordz.dictionary.utils.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.saulordz.dictionary.base.BaseActivityTest
import org.junit.Test

class ViewExtensionsTest : BaseActivityTest() {

  private val mockInputMethodManager = mock<InputMethodManager>()

  private val view = View(application)

  @Test
  fun testMakeVisible() = with(view) {
    visibility = View.GONE

    makeVisible()

    assertThat(visibility).isEqualTo(View.VISIBLE)
  }


  @Test
  fun testMakeGone() = with(view) {
    visibility = View.VISIBLE

    makeGone()

    assertThat(visibility).isEqualTo(View.GONE)
  }

  @Test
  fun testHideInputKeyboard(): Unit = with(view) {
    hideInputKeyboard(mockInputMethodManager)

    verify(mockInputMethodManager).hideSoftInputFromWindow(windowToken, 0)
  }
}