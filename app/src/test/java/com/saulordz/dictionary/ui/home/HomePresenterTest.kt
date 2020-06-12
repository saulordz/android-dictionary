package com.saulordz.dictionary.ui.home

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
  private val mockView = mock<HomeContract.View>()
  private val presenter = HomePresenter(schedulerComposer, mockGoogleDictionaryRepository)

  @Before
  fun setUp() {
    presenter.attachView(mockView)
  }

  @Test
  fun testSearchSuccess() {
    doReturn(Single.just(listOf(mockWord))).whenever(mockGoogleDictionaryRepository).singleSearchWord(any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(TEST_SEARCH_TERM)
    verify(mockView).searchTerm
    verify(mockView).words = listOf(mockWord)
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository, mockWord)
  }

  @Test
  fun testSearchWithError() {
    doReturn(Single.error<IllegalStateException>(IllegalStateException()))
      .whenever(mockGoogleDictionaryRepository).singleSearchWord(any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(TEST_SEARCH_TERM)
    verify(mockView).searchTerm
    verify(mockView).displayError()
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository)
  }

  private companion object {
    private const val TEST_SEARCH_TERM = "keyword"
    private const val TEST_WORD = "wordddd"
  }
}