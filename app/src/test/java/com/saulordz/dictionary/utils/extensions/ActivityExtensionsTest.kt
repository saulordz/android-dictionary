package com.saulordz.dictionary.utils.extensions

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.nhaarman.mockito_kotlin.verify
import com.saulordz.dictionary.base.BaseActivityTest
import org.junit.Test
import org.mockito.Mock

class ActivityExtensionsTest : BaseActivityTest() {

  @Mock lateinit var mockInputMethodManager: InputMethodManager
  
  @Test
  fun testHideInputKeyboard() = letActivity<AppCompatActivity> {
    it.hideInputKeyboard(mockInputMethodManager)

    verify(mockInputMethodManager).hideSoftInputFromWindow(it.window.decorView.windowToken, 0)
  }

}