package com.saulordz.dictionary.data.remote

import com.saulordz.dictionary.data.model.Word
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GoogleDictionaryService {

  @GET("api/v1/entries/{language}/{searchTerm}")
  fun searchWord(@Path(value = "language") language: String, @Path(value = "searchTerm") searchTerm: String): Single<Response<List<Word>>>
}