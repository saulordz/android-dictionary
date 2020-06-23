package com.saulordz.dictionary.ui.home

import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.nhaarman.mockito_kotlin.*
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.data.repository.GoogleDictionaryRepository
import com.saulordz.dictionary.data.repository.SharedPreferencesRepository
import com.saulordz.dictionary.testUtils.RxTestUtils.schedulerComposer
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
  private val mockGoogleDictionaryRepository = mock<GoogleDictionaryRepository>()
  private val mockSharedPreferencesRepository = mock<SharedPreferencesRepository>()
  private val mockTextViewEditorActionEvent = mock<TextViewEditorActionEvent> {
    on { actionId } doReturn EditorInfo.IME_ACTION_SEARCH
  }

  private val mockView = mock<HomeContract.View>()

  private val presenter = HomePresenter(schedulerComposer, mockGoogleDictionaryRepository, mockSharedPreferencesRepository)

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
    verify(mockView).languageSelectionStates = expectedLanguageSelectionStateList
    verifyNoMoreInteractions(mockView, mockSharedPreferencesRepository)
  }

  @Test
  fun testInitializeWithUserPreferences() {
    doReturn(Language.SPANISH).whenever(mockSharedPreferencesRepository).getUserPreferredLanguage()
    val expectedLanguageSelectionStateList = LanguageSelectionStateMapper(Language.SPANISH)

    presenter.initialize()

    verify(mockSharedPreferencesRepository).getUserPreferredLanguage()
    verify(mockView).languageSelectionStates = expectedLanguageSelectionStateList
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
    doReturn(Single.just(listOf(mockWord))).whenever(mockGoogleDictionaryRepository).singleSearchWord(any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(mockTextViewEditorActionEvent)

    presenter.registerSearchEditorActionEvent(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(TEST_SEARCH_TERM)
    verify(mockTextViewEditorActionEvent).actionId
    verify(mockView).hideKeyboard()
    verify(mockView).showProgress()
    verify(mockView).searchTerm
    verify(mockView).words = listOf(mockWord)
    verify(mockView).hideProgress()
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository, mockWord, mockTextViewEditorActionEvent)
  }

  @Test
  fun testRegisterSearchButtonObservableSuccess() {
    doReturn(Single.just(listOf(mockWord))).whenever(mockGoogleDictionaryRepository).singleSearchWord(any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(TEST_SEARCH_TERM)
    verify(mockView).hideKeyboard()
    verify(mockView).showProgress()
    verify(mockView).searchTerm
    verify(mockView).words = listOf(mockWord)
    verify(mockView).hideProgress()
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository, mockWord)
  }

  @Test
  fun testSearchWithEmptySearchTerm() {
    doReturn("").whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockView).searchTerm
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository)
  }

  @Test
  fun testSearchWithError() {
    doReturn(Single.error<IllegalStateException>(IllegalStateException()))
      .whenever(mockGoogleDictionaryRepository).singleSearchWord(any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(TEST_SEARCH_TERM)
    verify(mockView).hideKeyboard()
    verify(mockView).showProgress()
    verify(mockView).searchTerm
    verify(mockView).hideProgress()
    verify(mockView).displayError()
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
    doReturn(listOf(mockLanguageSelectionState)).whenever(mockView).languageSelectionStates

    presenter.handleNewLanguageApplied()

    verify(mockSharedPreferencesRepository).saveUserPreferredLanguage(Language.SPANISH)
    verify(mockLanguageSelectionState).selected
    verify(mockLanguageSelectionState).language
    verify(mockView).languageSelectionStates
    verify(mockView).applyNewLanguage(Language.SPANISH)
    verifyNoMoreInteractions(mockView, mockLanguageSelectionState, mockSharedPreferencesRepository)
  }

  private companion object {
    private const val TEST_SEARCH_TERM = "keyword"
    private const val TEST_WORD = "wordddd"
  }
}