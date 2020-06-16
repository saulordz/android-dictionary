package com.saulordz.dictionary.ui.home

import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.nhaarman.mockito_kotlin.*
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.data.repository.GoogleDictionaryRepository
import com.saulordz.dictionary.rx.SchedulerComposer
import com.saulordz.dictionary.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class HomePresenterTest {

  private val schedulerComposer = SchedulerComposer(object : SchedulerProvider {
    override val io = Schedulers.trampoline()
    override val ui = Schedulers.trampoline()
  })

  private val mockWord = mock<Word> {
    on { formattedWord } doReturn TEST_WORD
  }
  private val mockGoogleDictionaryRepository = mock<GoogleDictionaryRepository>()
  private val mockTextViewEditorActionEvent = mock<TextViewEditorActionEvent> {
    on { actionId } doReturn EditorInfo.IME_ACTION_SEARCH
  }
  private val mockView = mock<HomeContract.View>()

  private val presenter = HomePresenter(schedulerComposer, mockGoogleDictionaryRepository)

  @Before
  fun setUp() {
    presenter.attachView(mockView)
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

  private companion object {
    private const val TEST_SEARCH_TERM = "keyword"
    private const val TEST_WORD = "wordddd"
  }
}