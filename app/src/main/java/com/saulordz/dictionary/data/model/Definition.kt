package com.saulordz.dictionary.data.model

import com.squareup.moshi.Json

data class Definition(
  @field:Json(name = "definition") val definition: String? = null,
  @field:Json(name = "examples") val examples: String? = null,
  @field:Json(name = "synonyms") val synonyms: List<String>? = null
)