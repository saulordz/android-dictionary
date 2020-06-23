package com.saulordz.dictionary.data.repository

import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.UserPreferences
import com.saulordz.dictionary.data.model.UserPreferencesStoreModel
import com.saulordz.dictionary.utils.extensions.orDefault
import javax.inject.Inject

class SharedPreferencesRepository @Inject constructor() {

  internal fun saveUserPreferredLanguage(language: Language) {
    val userPreference = getUserPreferences()
      .copy(preferredLanguage = language)
    UserPreferencesStoreModel(userPreference).save()
  }

  internal fun getUserPreferredLanguage() =
    getUserPreferences()
      .preferredLanguage


  private fun getUserPreferences() =
    UserPreferencesStoreModel()
      .get()
      .orDefault(UserPreferences.DEFAULT_USER_PREFERENCES)
}

