package com.saulordz.dictionary.ui.home

import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.nhaarman.mockito_kotlin.*
import com.saulordz.dictionary.base.BasePresenter.Companion.FEEDBACK_EMAIL_ADDRESS
import com.saulordz.dictionary.base.BasePresenter.Companion.FEEDBACK_EMAIL_SUBJECT
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.RecentSearch
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.data.repository.GoogleDictionaryRepository
import com.saulordz.dictionary.data.repository.SharedPreferencesRepository
import com.saulordz.dictionary.testUtils.RxTestUtils.testSchedulerComposer
import com.saulordz.dictionary.ui.home.dialog.LanguageSelectionStateMapper
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class HomePresenterTest {

  private val mockWord = mock<Word> {
    on { formattedWord } doReturn TEST_WORD
  }
  private val mockLanguageSelectionState = mock<LanguageSelectionState> {
    on { language } doReturn Language.SPANISH
    on { selected } doReturn true
  }
  private val mockRecentSearch = mock<RecentSearch>()
  private val mockGoogleDictionaryRepository = mock<GoogleDictionaryRepository>()
  private val mockSharedPreferencesRepository = mock<SharedPreferencesRepository> {
    on { getRecentSearches() } doReturn listOf(mockRecentSearch)
  }
  private val mockTextViewEditorActionEvent = mock<TextViewEditorActionEvent> {
    on { actionId } doReturn EditorInfo.IME_ACTION_SEARCH
  }

  private val mockView = mock<HomeContract.View> {
    on { languageSelectionStates } doReturn listOf(mockLanguageSelectionState)
  }

  private val presenter = HomePresenter(testSchedulerComposer, mockGoogleDictionaryRepository, mockSharedPreferencesRepository)

  @Before
  fun setUp() {
    presenter.attachView(mockView)
  }

  @Test
  fun testInitializeWithNoUserPreferences() {
    doReturn(Language.DEFAULT_LANGUAGE).whenever(mockSharedPreferencesRepository).getUserPreferredLanguage()
    val expectedLanguageSelectionStateList = LanguageSelectionStateMapper(Language.DEFAULT_LANGUAGE)

    presenter.initialize()

    verify(mockSharedPreferencesRepository).getUserPreferredLanguage()
    verify(mockSharedPreferencesRepository).getRecentSearches()
    verify(mockView).languageSelectionStates = expectedLanguageSelectionStateList
    verify(mockView).setRecentSearches(listOf(mockRecentSearch))
    verifyNoMoreInteractions()
  }

  @Test
  fun testInitializeWithUserPreferences() {
    doReturn(Language.SPANISH).whenever(mockSharedPreferencesRepository).getUserPreferredLanguage()
    val expectedLanguageSelectionStateList = LanguageSelectionStateMapper(Language.SPANISH)

    presenter.initialize()

    verify(mockSharedPreferencesRepository).getUserPreferredLanguage()
    verify(mockSharedPreferencesRepository).getRecentSearches()
    verify(mockView).languageSelectionStates = expectedLanguageSelectionStateList
    verify(mockView).setRecentSearches(listOf(mockRecentSearch))
    verifyNoMoreInteractions()
  }

  @Test
  fun testRegisterSearchEditorActionObservableWithDoneAction() {
    doReturn(EditorInfo.IME_ACTION_DONE).whenever(mockTextViewEditorActionEvent).actionId
    val clickObservable = Observable.just(mockTextViewEditorActionEvent)

    presenter.registerSearchEditorActionEvent(clickObservable)

    verify(mockTextViewEditorActionEvent).actionId
    verifyNoMoreInteractions()
  }

  @Test
  fun testRegisterSearchEditorActionObservableSuccess() {
    doReturn(Single.just(listOf(mockWord))).whenever(mockGoogleDictionaryRepository).singleSearchWord(any(), any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(mockTextViewEditorActionEvent)

    presenter.registerSearchEditorActionEvent(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(Language.SPANISH.languageTag, TEST_SEARCH_TERM)
    verify(mockSharedPreferencesRepository).getRecentSearches()
    verify(mockSharedPreferencesRepository).addRecentSearch(argThat {
      searchTerm == TEST_SEARCH_TERM &&
          language == Language.SPANISH &&
          words == listOf(mockWord)
    })
    verify(mockTextViewEditorActionEvent).actionId
    verifyPrepareSearchInteractions()
    verifySearchSuccessInteractions()
    verify(mockView).setRecentSearches(listOf(mockRecentSearch))
    verifyLanguageSelectionInteractions()
    verifyNoMoreInteractions()
  }

  @Test
  fun testRegisterSearchButtonObservableSuccess() {
    doReturn(Single.just(listOf(mockWord))).whenever(mockGoogleDictionaryRepository).singleSearchWord(any(), any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(Language.SPANISH.languageTag, TEST_SEARCH_TERM)
    verify(mockSharedPreferencesRepository).getRecentSearches()
    verify(mockSharedPreferencesRepository).addRecentSearch(argThat {
      searchTerm == TEST_SEARCH_TERM &&
          language == Language.SPANISH &&
          words == listOf(mockWord)
    })
    verifyPrepareSearchInteractions()
    verifySearchSuccessInteractions()
    verify(mockView).setRecentSearches(listOf(mockRecentSearch))
    verifyLanguageSelectionInteractions()
    verifyNoMoreInteractions()
  }

  @Test
  fun testSearchWithEmptySearchTerm() {
    doReturn("").whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockView).languageSelectionStates
    verify(mockView).searchTerm
    verifyLanguageSelectionInteractions()
    verifyNoMoreInteractions()
  }

  @Test
  fun testSearchWithNullLanguageSelectionStates() {
    doReturn(Single.just(listOf(mockWord))).whenever(mockGoogleDictionaryRepository).singleSearchWord(any(), any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    doReturn(null).whenever(mockView).languageSelectionStates
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockSharedPreferencesRepository).getRecentSearches()
    verify(mockSharedPreferencesRepository).addRecentSearch(argThat {
      searchTerm == TEST_SEARCH_TERM &&
          language == Language.DEFAULT_LANGUAGE &&
          words == listOf(mockWord)
    })
    verify(mockGoogleDictionaryRepository).singleSearchWord(Language.DEFAULT_LANGUAGE.languageTag, TEST_SEARCH_TERM)
    verifyPrepareSearchInteractions()
    verifySearchSuccessInteractions()
    verify(mockView).setRecentSearches(listOf(mockRecentSearch))
    verifyNoMoreInteractions()
  }

  @Test
  fun testSearchWithNoLanguageStateSelectionSelected() {
    doReturn(Single.just(listOf(mockWord))).whenever(mockGoogleDictionaryRepository).singleSearchWord(any(), any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    doReturn(false).whenever(mockLanguageSelectionState).selected
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(Language.DEFAULT_LANGUAGE.languageTag, TEST_SEARCH_TERM)
    verify(mockSharedPreferencesRepository).getRecentSearches()
    verify(mockSharedPreferencesRepository).addRecentSearch(argThat {
      searchTerm == TEST_SEARCH_TERM &&
          language == Language.DEFAULT_LANGUAGE &&
          words == listOf(mockWord)
    })
    verifyPrepareSearchInteractions()
    verifySearchSuccessInteractions()
    verify(mockView).setRecentSearches(listOf(mockRecentSearch))
    verify(mockLanguageSelectionState).selected
    verifyNoMoreInteractions()
  }

  @Test
  fun testSearchWithError() {
    doReturn(Single.error<IllegalStateException>(IllegalStateException()))
      .whenever(mockGoogleDictionaryRepository).singleSearchWord(any(), any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(Language.SPANISH.languageTag, TEST_SEARCH_TERM)
    verifyPrepareSearchInteractions()
    verify(mockView).hideProgress()
    verify(mockView).showDefinitionNotFoundError()
    verifyLanguageSelectionInteractions()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleLanguageMenuItemSelected() {
    presenter.handleLanguageMenuItemSelected()

    verify(mockView).showLanguageSelector()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleFeedbackMenuItemSelected() {
    presenter.handleFeedbackMenuItemSelected()

    verify(mockView).startEmailIntent(FEEDBACK_EMAIL_ADDRESS, FEEDBACK_EMAIL_SUBJECT)
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleAboutMenuItemSelected() {
    presenter.handleAboutMenuItemSelected()

    verify(mockView).startAboutActivity()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleLanguageClicked() {
    presenter.handleLanguageClicked(mockLanguageSelectionState)

    verify(mockLanguageSelectionState).language
    verify(mockView).languageSelectionStates = argThat {
      size == Language.values().size &&
          count { it.selected } == 1 &&
          find { it.selected }!!.language == Language.SPANISH
    }
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleLanguageClickedWithNullClickedLanguage() {
    presenter.handleLanguageClicked(null)

    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleNewLanguageApplied() {
    presenter.handleNewLanguageApplied()

    verify(mockSharedPreferencesRepository).saveUserPreferredLanguage(Language.SPANISH)
    verify(mockLanguageSelectionState).selected
    verify(mockLanguageSelectionState).language
    verify(mockView).languageSelectionStates
    verify(mockView).recreateView()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleNewLanguageAppliedWithError() {
    doReturn(null).whenever(mockView).languageSelectionStates

    presenter.handleNewLanguageApplied()

    verify(mockView).languageSelectionStates
    verify(mockView).showLanguageSelectionError()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleRateMenuItemSelected() {
    presenter.handleRateMenuItemSelected()

    verify(mockView).startPlayStoreIntent()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleRecentSearchClicked() {
    doReturn(listOf(mockWord)).whenever(mockRecentSearch).words
    doReturn(TEST_SEARCH_TERM).whenever(mockRecentSearch).searchTerm
    doReturn(Language.DEFAULT_LANGUAGE).whenever(mockRecentSearch).language

    presenter.handleRecentSearchClicked(mockRecentSearch)

    verify(mockSharedPreferencesRepository).getRecentSearches()
    verify(mockSharedPreferencesRepository).addRecentSearch(argThat {
      searchTerm == TEST_SEARCH_TERM &&
          language == Language.DEFAULT_LANGUAGE &&
          words == listOf(mockWord)
    })
    verify(mockRecentSearch).searchTerm
    verify(mockRecentSearch).language
    verify(mockRecentSearch).words
    verify(mockView).setRecentSearches(listOf(mockRecentSearch))
    verifySearchSuccessInteractions()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleRecentSearchClickedWithNullField() {
    doReturn(null).whenever(mockRecentSearch).words

    presenter.handleRecentSearchClicked(mockRecentSearch)

    verify(mockRecentSearch).searchTerm
    verify(mockRecentSearch).language
    verify(mockRecentSearch).words
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleBackPressedWithMenuDisplayed() {
    doReturn(true).whenever(mockView).isMenuDisplayed

    presenter.handleBackPressed()

    verify(mockView).isMenuDisplayed
    verify(mockView).hideMenu()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleBackPressedWithDefinitionDisplayed() {
    doReturn(true).whenever(mockView).isDefinitionDisplayed

    presenter.handleBackPressed()

    verify(mockView).isMenuDisplayed
    verify(mockView).isDefinitionDisplayed
    verify(mockView).setWords(null)
    verify(mockView).showRecentSearches()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleBackPressedWithExit() {
    presenter.handleBackPressed()

    verify(mockView).isMenuDisplayed
    verify(mockView).isDefinitionDisplayed
    verify(mockView).exit()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleHomePressedWithDefinitionDisplayed() {
    doReturn(true).whenever(mockView).isDefinitionDisplayed

    presenter.handleHomePressed()

    verify(mockView).isDefinitionDisplayed
    verify(mockView).setWords(null)
    verify(mockView).showRecentSearches()
    verifyNoMoreInteractions()
  }

  @Test
  fun testHandleHomePressedWithRecentDisplayed() {
    presenter.handleHomePressed()

    verify(mockView).isDefinitionDisplayed
    verify(mockView).showMenu()
    verifyNoMoreInteractions()
  }

  private fun verifyPrepareSearchInteractions() {
    verify(mockView).hideKeyboard()
    verify(mockView).showProgress()
    verify(mockView).languageSelectionStates
    verify(mockView).searchTerm
  }

  private fun verifySearchSuccessInteractions() {
    verify(mockView).setWords(listOf(mockWord))
    verify(mockView).hideProgress()
    verify(mockView).showBackMenu()
    verify(mockView).showSearchResults()
  }

  private fun verifyLanguageSelectionInteractions() {
    verify(mockLanguageSelectionState).selected
    verify(mockLanguageSelectionState).language
  }

  private fun verifyNoMoreInteractions() = verifyNoMoreInteractions(
    mockView,
    mockWord,
    mockRecentSearch,
    mockGoogleDictionaryRepository,
    mockSharedPreferencesRepository,
    mockTextViewEditorActionEvent,
    mockLanguageSelectionState
  )

  private companion object {
    private const val TEST_SEARCH_LANGUAGE = "keyLanguage"
    private const val TEST_SEARCH_TERM = "keyword"
    private const val TEST_WORD = "wordddd"
  }
}