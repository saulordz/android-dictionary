package com.saulordz.dictionary.ui.home

import android.content.Intent.ACTION_SENDTO
import android.content.Intent.ACTION_VIEW
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.GravityCompat
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.afollestad.materialdialogs.MaterialDialog
import com.nhaarman.mockito_kotlin.*
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.RecentSearch
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.testUtils.assertions.*
import com.saulordz.dictionary.ui.about.AboutActivity
import com.saulordz.dictionary.utils.extensions.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_main_toolbar.*
import org.junit.Test
import org.mockito.Mock
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowToast
import kotlin.reflect.jvm.jvmName


class HomeActivityTest : BaseActivityTest() {

  private val mockRecentSearch = mock<RecentSearch>()
  private val mockWord = mock<Word>()
  private val mockLanguageSelectionState = mock<LanguageSelectionState>()

  @Mock lateinit var mockPresenter: HomePresenter
  @Mock lateinit var mockInputMethodManager: InputMethodManager

  @Test
  fun testOnCreateInteractions() = letActivity<HomeActivity> {
    verifyPresenterOnCreateInteractions(it)
    verifyNoMoreInteractions(mockPresenter)
  }

  @Test
  fun testGetSearchTerm() = letActivity<HomeActivity> {
    it.a_home_search_input.setText(TEST_SEARCH_TERM)

    val actual = it.searchTerm

    assertThat(actual).isEqualTo(TEST_SEARCH_TERM)
  }

  @Test
  fun testSetWords() = letActivity<HomeActivity> {
    it.wordAdapter.submitList(null)

    it.setWords(listOf(mockWord))

    assertThat(it.wordAdapter.itemCount).isEqualTo(1)
  }

  @Test
  fun testSetRecentSearches() = letActivity<HomeActivity> {
    it.recentSearchAdapter.submitList(null)

    it.setRecentSearches(listOf(mockRecentSearch))

    assertThat(it.recentSearchAdapter.itemCount).isEqualTo(1)
  }

  @Test
  fun testSetLanguageSelectionStates() = letActivity<HomeActivity> {
    it.languageSelectionStates = null

    it.languageSelectionStates = listOf(mockLanguageSelectionState)

    assertThat(it.languageAdapter.itemCount).isEqualTo(1)
  }

  @Test
  fun testGetLanguageSelectionStates() = letActivity<HomeActivity> {
    it.languageSelectionStates = listOf(mockLanguageSelectionState)

    val actual = it.languageSelectionStates

    assertThat(actual).isEqualTo(listOf(mockLanguageSelectionState))
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
    verifyNoMoreInteractions(mockInputMethodManager)
  }

  @Test
  fun testOnHomePressedClosesKeyboard() = letActivity<HomeActivity> {
    it.onHomePressed()

    verifyPresenterOnCreateInteractions(it)
    verify(mockPresenter).handleHomePressed()
    verifyNoMoreInteractions(mockPresenter)
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

    verifyPresenterOnCreateInteractions(it)
    verify(mockPresenter).handleNewLanguageApplied()
    verifyNoMoreInteractions(mockPresenter)
  }

  @Test
  fun testOnLanguageClickedListener() = letActivity<HomeActivity> {
    it.onLanguageClickedListener.invoke(mockLanguageSelectionState)

    verifyPresenterOnCreateInteractions(it)
    verify(mockPresenter).handleLanguageClicked(mockLanguageSelectionState)
    verifyNoMoreInteractions(mockPresenter)
  }

  @Test
  fun testLanguageMenuItemSelected() = letActivity<HomeActivity> {
    val mockMenuItem = mock<MenuItem> {
      on { itemId } doReturn R.id.m_main_language
    }

    it.onNavigationItemSelectedListener(mockMenuItem)

    verifyPresenterOnCreateInteractions(it)
    verify(mockPresenter).handleLanguageMenuItemSelected()
    verify(mockMenuItem).itemId
    verifyNoMoreInteractions(mockPresenter, mockMenuItem)
  }

  @Test
  fun testAboutMenuItemSelected() = letActivity<HomeActivity> {
    val mockMenuItem = mock<MenuItem> {
      on { itemId } doReturn R.id.m_main_about
    }

    it.onNavigationItemSelectedListener(mockMenuItem)

    verifyPresenterOnCreateInteractions(it)
    verify(mockPresenter).handleAboutMenuItemSelected()
    verify(mockMenuItem).itemId
    verifyNoMoreInteractions(mockPresenter, mockMenuItem)
  }

  @Test
  fun testRateMenuItemSelected() = letActivity<HomeActivity> {
    val mockMenuItem = mock<MenuItem> {
      on { itemId } doReturn R.id.m_main_rate_app
    }

    it.onNavigationItemSelectedListener(mockMenuItem)

    verifyPresenterOnCreateInteractions(it)
    verify(mockPresenter).handleRateMenuItemSelected()
    verify(mockMenuItem).itemId
    verifyNoMoreInteractions(mockPresenter, mockMenuItem)
  }

  @Test
  fun testFeedbackMenuItemSelected() = letActivity<HomeActivity> {
    val mockMenuItem = mock<MenuItem> {
      on { itemId } doReturn R.id.m_main_feedback
    }

    it.onNavigationItemSelectedListener(mockMenuItem)

    verifyPresenterOnCreateInteractions(it)
    verify(mockPresenter).handleFeedbackMenuItemSelected()
    verify(mockMenuItem).itemId
    verifyNoMoreInteractions(mockPresenter, mockMenuItem)
  }

  @Test
  fun testUnknownMenuItemSelected() = letActivity<HomeActivity> {
    val mockMenuItem = mock<MenuItem> {
      on { itemId } doReturn R.id.a_home_search_input
    }

    it.onNavigationItemSelectedListener(mockMenuItem)

    verifyPresenterOnCreateInteractions(it)
    verify(mockMenuItem).itemId
    verifyNoMoreInteractions(mockPresenter, mockMenuItem)
  }

  @Test
  fun testStartEmailIntent() = letActivity<HomeActivity> {
    it.startEmailIntent(TEST_EMAIL, TEST_SUBJECT)

    val intent = shadowOf(application).nextStartedActivity

    assertThat(intent).hasAction(ACTION_SENDTO)
    assertThat(intent).hasData(EXPECTED_EMAIL_URI)
  }

  @Test
  fun testStartPlayStoreIntent() = letActivity<HomeActivity> {
    val expectedUri = HomeActivity.PLAY_STORE_BASE_URL + application.packageName
    it.startPlayStoreIntent()

    val intent = shadowOf(application).nextStartedActivity

    assertThat(intent).hasAction(ACTION_VIEW)
    assertThat(intent).hasData(expectedUri)
  }

  @Test
  fun testStartAboutActivity() = letActivity<HomeActivity> {
    it.startAboutActivity()

    val intent = shadowOf(application).nextStartedActivity

    assertThat(intent).hasComponentClass(AboutActivity::class.jvmName)
  }

  @Test
  fun testIsDefinitionDisplayedWithTrue() = letActivity<HomeActivity> {
    it.a_home_view_flipper.showView(it.a_home_word_recycler)

    val actual = it.isDefinitionDisplayed

    assertThat(actual).isTrue()
  }

  @Test
  fun testIsDefinitionDisplayedWithFalse() = letActivity<HomeActivity> {
    it.a_home_view_flipper.showView(it.a_home_recent_search_recycler)

    val actual = it.isDefinitionDisplayed

    assertThat(actual).isFalse()
  }

  @Test
  fun testIsMenuShownWithTrue() = letActivity<HomeActivity> {
    it.a_base_drawer.openDrawer(GravityCompat.START)

    val actual = it.isMenuDisplayed

    assertThat(actual).isTrue()
  }

  @Test
  fun testIsMenuShownWithFalse() = letActivity<HomeActivity> {
    it.a_base_drawer.closeDrawer(GravityCompat.START)

    val actual = it.isMenuDisplayed

    assertThat(actual).isFalse()
  }

  @Test
  fun testOnBackPressed() = letActivity<HomeActivity> {
    it.onBackPressed()

    verifyPresenterOnCreateInteractions(it)
    verify(mockPresenter).handleBackPressed()
    verifyNoMoreInteractions(mockPresenter)
  }

  @Test
  fun testShowDefinitionNotFoundError() = letActivity<HomeActivity> {
    it.a_home_search_input.setText(TEST_WORD)
    val expectedText = it.getString(R.string.word_not_found_error, TEST_WORD)

    it.showDefinitionNotFoundError()

    assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo(expectedText)
  }

  @Test
  fun testShowLanguageSelectionError() = letActivity<HomeActivity> {
    val expectedText = it.getString(R.string.language_selection_error)

    it.showLanguageSelectionError()

    assertThat(ShadowToast.getTextOfLatestToast()).isEqualTo(expectedText)
  }

  @Test
  fun testShowRecentSearches() = letActivity<HomeActivity> {
    it.a_home_view_flipper.showView(it.a_home_word_recycler)

    it.showRecentSearches()

    assertThat(it.a_home_view_flipper).isShowing(it.a_home_recent_search_recycler)
  }


  @Test
  fun testShowSearchResults() = letActivity<HomeActivity> {
    it.a_home_view_flipper.showView(it.a_home_recent_search_recycler)

    it.showSearchResults()

    assertThat(it.a_home_view_flipper).isShowing(it.a_home_word_recycler)
  }

  @Test
  fun testShowBackMenu() = letActivity<HomeActivity> {
    it.createDrawerToolbar()

    it.showBackMenu()

    assertThat(it.i_main_toolbar).isBackToolbar()
  }

  @Test
  fun testHideMenu() = letActivity<HomeActivity> {
    it.openDrawer()

    it.hideMenu()

    assertThat(it.isDrawerOpen).isFalse()
  }

  @Test
  fun testShowMenu() = letActivity<HomeActivity> {
    it.closeDrawer()

    it.showMenu()

    assertThat(it.isDrawerOpen).isTrue()
  }

  @Test
  fun testExit() = letActivity<HomeActivity> {
    it.exit()

    assertThat(it.isFinishing).isTrue()
  }

  private fun verifyPresenterOnCreateInteractions(view: HomeContract.View) {
    verify(mockPresenter).attachView(view)
    verify(mockPresenter).initialize()
    verify(mockPresenter).registerSearchButtonObservable(any())
    verify(mockPresenter).registerSearchEditorActionEvent(any())
  }

  private companion object {
    private const val TEST_SEARCH_TERM = "search_term"
    private const val TEST_WORD = "word"
    private const val TEST_EMAIL = "em@i.l"
    private const val TEST_SUBJECT = "sub_ject"

    private const val EXPECTED_EMAIL_URI = "mailto:$TEST_EMAIL?subject=$TEST_SUBJECT"
  }
}