package com.saulordz.dictionary.data.model

import com.codeblin.annotations.Document

@Document
data class UserPreferences(
  val preferredLanguage: Language
) {
  internal companion object {
    internal val DEFAULT_USER_PREFERENCES = UserPreferences(Language.DEFAULT_LANGUAGE)
  }
}