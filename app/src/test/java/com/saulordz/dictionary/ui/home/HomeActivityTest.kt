package com.saulordz.dictionary.ui.home

import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.afollestad.materialdialogs.MaterialDialog
import com.nhaarman.mockito_kotlin.*
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.testUtils.hasItemCount
import com.saulordz.dictionary.testUtils.hasNegativeText
import com.saulordz.dictionary.testUtils.hasPositiveText
import com.saulordz.dictionary.testUtils.hasTitle
import com.saulordz.dictionary.utils.extensions.makeGone
import com.saulordz.dictionary.utils.extensions.makeVisible
import kotlinx.android.synthetic.main.activity_home.*
import org.junit.Test
import org.mockito.Mock
import org.robolectric.shadows.ShadowAlertDialog


class HomeActivityTest : BaseActivityTest() {

  private val mockWord = mock<Word>()
  private val mockLanguageSelectionState = mock<LanguageSelectionState>()

  @Mock lateinit var mockHomePresenter: HomePresenter
  @Mock lateinit var mockInputMethodManager: InputMethodManager

  @Test
  fun testOnCreateInteractions() = letActivity<HomeActivity> {
    verify(mockHomePresenter).attachView(it)
    verify(mockHomePresenter).initialize()
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
  fun testSetLanguageSelectionStates() = letActivity<HomeActivity> {
    it.languageSelectionStates = null

    it.languageSelectionStates = listOf(mockLanguageSelectionState)

    assertThat(it.languageAdapter.itemCount).isEqualTo(1)
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

  @Test
  fun testShowLanguageSelector() = letActivity<HomeActivity> {
    it.languageSelectionStates = listOf(mockLanguageSelectionState)

    it.showLanguageSelector()

    val dialog = ShadowAlertDialog.getLatestDialog() as MaterialDialog
    assertThat(dialog.isShowing).isTrue()
    assertThat(dialog).hasTitle(application.getString(R.string.message_select_language))
    assertThat(dialog).hasPositiveText(application.getString(R.string.message_apply))
    assertThat(dialog).hasNegativeText(application.getString(R.string.message_cancel))
    assertThat(dialog).hasItemCount(1)
  }

  @Test
  fun testOnApplyLanguageListener() = letActivity<HomeActivity> {
    val mockMaterialDialog = mock<MaterialDialog>()

    it.onApplyLanguageListener.invoke(mockMaterialDialog)

    verify(mockHomePresenter).handleNewLanguageApplied()
  }

  @Test
  fun testOnLanguageClickedListener() = letActivity<HomeActivity> {
    it.onLanguageClickedListener.invoke(mockLanguageSelectionState)

    verify(mockHomePresenter).handleLanguageClicked(mockLanguageSelectionState)
  }

  @Test
  fun testOnLanguageMenuItemSelected() = letActivity<HomeActivity> {
    val mockMenuItem = mock<MenuItem> {
      on { itemId } doReturn R.id.m_main_language
    }

    it.onNavigationItemSelectedListener(mockMenuItem)

    verify(mockHomePresenter).handleLanguageMenuItemSelected()
  }

  private companion object {
    private const val TEST_SEARCH_TERM = "search_term"
    private const val TEST_WORD = "word"
  }
}