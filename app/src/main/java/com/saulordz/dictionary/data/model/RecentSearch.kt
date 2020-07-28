package com.saulordz.dictionary.data.model

data class RecentSearch(
  val searchTerm: String? = null,
  val language: Language? = null,
  val words: List<Word>? = null
)