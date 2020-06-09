package com.saulordz.dictionary.ui.home

import com.nhaarman.mockito_kotlin.*
import com.saulordz.dictionary.data.model.Definition
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

  private val mockDefinitionOne = mock<Definition> {
    on { definition } doReturn TEST_DEFINITION_ONE
  }
  private val mockDefinitionTwo = mock<Definition> {
    on { definition } doReturn TEST_DEFINITION_TWO
  }
  private val mockWord = mock<Word> {
    on { word } doReturn TEST_WORD
    on { definitions } doReturn mapOf(TEST_CATEGORY_ONE to listOf(mockDefinitionOne), TEST_CATEGORY_TWO to listOf(mockDefinitionTwo))
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
    doReturn(Single.just(mockWord)).whenever(mockGoogleDictionaryRepository).singleSearchWord(any())
    doReturn(TEST_SEARCH_TERM).whenever(mockView).searchTerm
    val clickObservable = Observable.just(Unit)

    presenter.registerSearchButtonObservable(clickObservable)

    verify(mockGoogleDictionaryRepository).singleSearchWord(TEST_SEARCH_TERM)
    verify(mockView).searchTerm
    verify(mockView).word = TEST_WORD
    verify(mockView).definitions = listOf(mockDefinitionOne, mockDefinitionTwo)
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
    verify(mockView).searchTerm
    verify(mockView).displayError()
    verifyNoMoreInteractions(mockView, mockGoogleDictionaryRepository)
  }

  private companion object {
    private const val TEST_SEARCH_TERM = "keyword"
    private const val TEST_WORD = "wordddd"
    private const val TEST_DEFINITION_ONE = "1234definition"
    private const val TEST_DEFINITION_TWO = "definitisagoon"
    private const val TEST_CATEGORY_ONE = "kategory"
    private const val TEST_CATEGORY_TWO = "categori"
  }
}