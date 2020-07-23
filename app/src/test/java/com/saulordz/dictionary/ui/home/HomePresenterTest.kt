package com.saulordz.dictionary.ui.home

import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.nhaarman.mockito_kotlin.*
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
    verify(mockView).recentSearches = listOf(mockRecentSearch)
    verifyNoMoreInteractions(mockView, mockSharedPreferencesRepository)
  }

  @Test
  fun testInitializeWithUserPreferences() {
    doReturn(Language.SPANISH).whenever(mockSharedPreferencesRepository).getUserPreferredLanguage()
    val expectedLanguageSelectionStateList = LanguageSelectionStateMapper(Language.SPANISH)

    presenter.initialize()

    verify(mockSharedPreferencesRepository).getUserPreferredLanguage()
    verify(mockSharedPreferencesRepository).getRecentSearches()
    verify(mockView).languageSelectionStates = expectedLanguageSelectionStateList
    verify(mockView).recentSearches = listOf(mockRecentSearch)
    verifyNoMoreInteractions(mockView, mockSharedPreferencesRepository)
  }

  @Test
  fun testRegisterSearchEditorActionObservableWithDoneAction() {
    doReturn(EditorInfo.IME_ACTION_DONE).whenever(mockTextViewEditorActionEvent).actionId
    val clickObservable = Observable.just(mockTextViewEditorActionEvent)

    presenter.registerSearchEditorActionEvent(clickObservable)

    verify(mockTextViewEditorActionEvent).actionId
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository, mockWord, mockTextViewEditorActionEvent)
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
    verify(mockView).hideKeyboard()
    verify(mockView).showProgress()
    verify(mockView).languageSelectionStates
    verify(mockView).searchTerm
    verify(mockView).words = listOf(mockWord)
    verify(mockView).hideProgress()
    verify(mockView).showBackMenu()
    verify(mockView).showSearchResults()
    verify(mockView).recentSearches = listOf(mockRecentSearch)
    verifyNoMoreInteractions(
      mockView,
      mockGoogleDictionaryRepository,
      mockWord,
      mockTextViewEditorActionEvent,
      mockSharedPreferencesRepository
    )
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
    verify(mockView).hideKeyboard()
    verify(mockView).showProgress()
    verify(mockView).languageSelectionStates
    verify(mockView).searchTerm
    verify(mockView).words = listOf(mockWord)
    verify(mockView).hideProgress()
    verify(mockView).showBackMenu()
    verify(mockView).showSearchResults()
    verify(mockView).recentSearches = listOf(mockRecentSearch)
    verifyNoMoreInteractions(
      mockView,
      mockGoogleDictionaryRepository,
      mockWord,
      mockSharedPreferencesRepository
    )
  }

  @Test
  fun testSearchWithEmptySearchTerm() {
    doReturn("").whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockView).languageSelectionStates
    verify(mockView).searchTerm
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository)
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
    verify(mockView).words = listOf(mockWord)
    verify(mockView).languageSelectionStates
    verify(mockView).searchTerm
    verify(mockView).hideKeyboard()
    verify(mockView).showProgress()
    verify(mockView).hideProgress()
    verify(mockView).showBackMenu()
    verify(mockView).showSearchResults()
    verify(mockView).recentSearches = any()
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository, mockWord, mockSharedPreferencesRepository)
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
    verify(mockView).hideKeyboard()
    verify(mockView).showProgress()
    verify(mockView).languageSelectionStates
    verify(mockView).searchTerm
    verify(mockView).words = listOf(mockWord)
    verify(mockView).hideProgress()
    verify(mockView).showBackMenu()
    verify(mockView).showSearchResults()
    verify(mockView).recentSearches = listOf(mockRecentSearch)
    verifyNoMoreInteractions(
      mockView,
      mockGoogleDictionaryRepository,
      mockWord,
      mockSharedPreferencesRepository
    )
  }

  @Test
  fun testSearchWithError() {
    doReturn(Single.error<IllegalStateException>(IllegalStateException()))
      .whenever(mockGoogleDictionaryRepository).singleSearchWord(any(), any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(Language.SPANISH.languageTag, TEST_SEARCH_TERM)
    verify(mockView).hideKeyboard()
    verify(mockView).showProgress()
    verify(mockView).languageSelectionStates
    verify(mockView).searchTerm
    verify(mockView).hideProgress()
    verify(mockView).showDefinitionNotFoundError()
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository)
  }

  @Test
  fun testHandleLanguageMenuItemSelected() {
    presenter.handleLanguageMenuItemSelected()

    verify(mockView).showLanguageSelector()
    verifyNoMoreInteractions(mockView)
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
    verifyNoMoreInteractions(mockView, mockLanguageSelectionState)
  }

  @Test
  fun testHandleLanguageClickedWithNullClickedLanguage() {
    presenter.handleLanguageClicked(null)

    verifyZeroInteractions(mockView)
  }

  @Test
  fun testHandleNewLanguageApplied() {
    presenter.handleNewLanguageApplied()

    verify(mockSharedPreferencesRepository).saveUserPreferredLanguage(Language.SPANISH)
    verify(mockLanguageSelectionState).selected
    verify(mockLanguageSelectionState).language
    verify(mockView).languageSelectionStates
    verify(mockView).recreateView()
    verifyNoMoreInteractions(mockView, mockLanguageSelectionState, mockSharedPreferencesRepository)
  }

  @Test
  fun testHandleNewLanguageAppliedWithError() {
    doReturn(null).whenever(mockView).languageSelectionStates

    presenter.handleNewLanguageApplied()

    verify(mockView).languageSelectionStates
    verify(mockView).showLanguageSelectionError()
    verifyNoMoreInteractions(mockView, mockLanguageSelectionState, mockSharedPreferencesRepository)
  }

  @Test
  fun testHandleRateMenuItemSelected() {
    presenter.handleRateMenuItemSelected()

    verify(mockView).startPlayStoreIntent()
    verifyNoMoreInteractions(mockView)
  }

  private companion object {
    private const val TEST_SEARCH_LANGUAGE = "keyLanguage"
    private const val TEST_SEARCH_TERM = "keyword"
    private const val TEST_WORD = "wordddd"
  }
}