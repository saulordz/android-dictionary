package com.saulordz.dictionary.data.model

import com.saulordz.dictionary.utils.extensions.stripSymbols
import com.squareup.moshi.Json

data class Word(
  @field:Json(name = "word") val word: String? = null,
  @field:Json(name = "phonetic") val phonetic: String? = null,
  @field:Json(name = "origin") val origin: String? = null,
  @field:Json(name = "meaning") val meanings: Map<String, List<Definition>>? = null
) {

  @delegate:Transient val formattedWord by lazy { word?.stripSymbols()?.capitalize() }
  @delegate:Transient val definitions by lazy { meanings?.values?.flatten() }
}