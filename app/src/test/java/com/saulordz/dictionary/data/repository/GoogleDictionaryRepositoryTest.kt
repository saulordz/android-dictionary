package com.saulordz.dictionary.data.repository

import com.nhaarman.mockito_kotlin.*
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.data.remote.GoogleDictionaryService
import com.saulordz.dictionary.data.repository.GoogleDictionaryRepository.Companion.ERROR_SEARCHING_FOR_WORD
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import retrofit2.Response

class GoogleDictionaryRepositoryTest {

  private val mockDictionaryService = mock<GoogleDictionaryService>()
  private val mockWord = mock<Word>()

  private val repository = GoogleDictionaryRepository(mockDictionaryService)

  @Test
  fun testSingleSearchWordWithSuccess() {
    doReturn(Single.just<Response<List<Word>>>(Response.success(listOf(mockWord))))
      .whenever(mockDictionaryService).searchWord(any())
    val observer = TestObserver<Word>()

    repository.singleSearchWord(TEST_SEARCH_TERM).subscribe(observer)

    verify(mockDictionaryService).searchWord(TEST_SEARCH_TERM)
    observer.assertValue(mockWord)
    observer.assertComplete()
  }

  @Test
  fun testSingleSearchWordWithErrorCode() {
    doReturn(Single.error<Response<List<Word>>>(IllegalStateException()))
      .whenever(mockDictionaryService).searchWord(any())
    val observer = TestObserver<Word>()

    repository.singleSearchWord(TEST_SEARCH_TERM).subscribe(observer)

    verify(mockDictionaryService).searchWord(TEST_SEARCH_TERM)
    observer.assertError { it is IllegalStateException }
  }

  @Test
  fun testSingleSearchWordWithNullBody() {
    doReturn(Single.just<Response<List<Word>>>(Response.success(null)))
      .whenever(mockDictionaryService).searchWord(any())
    val observer = TestObserver<Word>()

    repository.singleSearchWord(TEST_SEARCH_TERM).subscribe(observer)

    verify(mockDictionaryService).searchWord(TEST_SEARCH_TERM)
    observer.assertError { it is IllegalStateException && it.message == ERROR_SEARCHING_FOR_WORD }
  }

  @Test
  fun testSingleSearchWordWithFirstElementNull() {
    doReturn(Single.just<Response<List<Word>>>(Response.success(emptyList())))
      .whenever(mockDictionaryService).searchWord(any())
    val observer = TestObserver<Word>()

    repository.singleSearchWord(TEST_SEARCH_TERM).subscribe(observer)

    verify(mockDictionaryService).searchWord(TEST_SEARCH_TERM)
    observer.assertNoValues()
  }

  private companion object {
    private const val TEST_SEARCH_TERM = "palabra"
  }
}