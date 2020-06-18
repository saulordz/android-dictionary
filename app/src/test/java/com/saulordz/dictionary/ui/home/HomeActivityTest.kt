package com.saulordz.dictionary.ui.home

import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.utils.extensions.makeGone
import com.saulordz.dictionary.utils.extensions.makeVisible
import kotlinx.android.synthetic.main.activity_home.*
import org.junit.Test
import org.mockito.Mock


class HomeActivityTest : BaseActivityTest() {

  private val mockWord = mock<Word>()

  @Mock lateinit var mockHomePresenter: HomePresenter
  @Mock lateinit var mockInputMethodManager: InputMethodManager

  @Test
  fun testOnCreateInteractions() = letActivity<HomeActivity> {
    verify(mockHomePresenter).attachView(it)
    verify(mockHomePresenter).registerSearchButtonObservable(any())
    verify(mockHomePresenter).registerSearchEditorActionEvent(any())
    verifyNoMoreInteractions(mockHomePresenter)
  }

  @Test
  fun testGetSearchTerm() = letActivity<HomeActivity> {
    it.a_home_search_input.setText(TEST_SEARCH_TERM)

    val actual = it.searchTerm

    assertThat(actual).isEqualTo(TEST_SEARCH_TERM)
  }

  @Test
  fun testSetWords() = letActivity<HomeActivity> {
    it.words = null

    it.words = listOf(mockWord)

    assertThat(it.wordAdapter.itemCount).isEqualTo(1)
  }

  @Test
  fun testShowProgress() = letActivity<HomeActivity> {
    it.a_home_spinner.makeGone()

    it.showProgress()

    assertThat(it.a_home_spinner.visibility).isEqualTo(View.VISIBLE)
  }

  @Test
  fun testHideProgress() = letActivity<HomeActivity> {
    it.a_home_spinner.makeVisible()

    it.hideProgress()

    assertThat(it.a_home_spinner.visibility).isEqualTo(View.GONE)
  }

  @Test
  fun testHideKeyboard() = letActivity<HomeActivity> {
    it.hideKeyboard()

    verify(mockInputMethodManager).hideSoftInputFromWindow(it.window.decorView.rootView.windowToken, 0)
  }

  @Test
  fun testOnOptionItemSelectedClosesKeyboard() = letActivity<HomeActivity> {
    val mockMenuItem = mock<MenuItem>()

    it.onOptionsItemSelected(mockMenuItem)

    verify(mockInputMethodManager).hideSoftInputFromWindow(it.window.decorView.rootView.windowToken, 0)
  }

  private companion object {
    private const val TEST_SEARCH_TERM = "search_term"
    private const val TEST_WORD = "word"
  }
}