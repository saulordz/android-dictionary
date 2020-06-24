package com.saulordz.dictionary.data.repository

import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.data.remote.GoogleDictionaryService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleDictionaryRepository @Inject constructor(
  private val googleDictionaryService: GoogleDictionaryService
) {

  fun singleSearchWord(language: String, searchTerm: String): Single<List<Word>> =
    googleDictionaryService.searchWord(language, searchTerm)
      .filter { it.isSuccessful && it.body() != null }
      .switchIfEmpty(Single.error(IllegalStateException(ERROR_SEARCHING_FOR_WORD)))
      .map { it.body()!! }

  internal companion object {
    internal const val ERROR_SEARCHING_FOR_WORD = "Error searching for word"
  }
}