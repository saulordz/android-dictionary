package com.saulordz.dictionary.data.model

import com.squareup.moshi.Json

data class Word(
  @field:Json(name = "word") val word: String? = null,
  @field:Json(name = "phonetic") val phonetic: String? = null,
  @field:Json(name = "origin") val origin: String? = null,
  @field:Json(name = "meaning") val definitions: Map<String, List<Definition>>? = null
)