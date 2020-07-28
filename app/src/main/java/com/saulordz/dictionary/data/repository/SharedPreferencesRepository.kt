package com.saulordz.dictionary.data.repository

import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.RecentSearch
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

  internal fun getRecentSearches() =
    getUserPreferences().recentSearches.orDefault(emptyList())

  internal fun addRecentSearch(recentSearch: RecentSearch) {
    val currentSearchHistory = getRecentSearches()
    val newSearchHistory = setOf(recentSearch) + currentSearchHistory
    val userPreference = getUserPreferences()
      .copy(recentSearches = newSearchHistory.take(MAX_RECENT_SEARCHES))
    UserPreferencesStoreModel(userPreference).save()
  }

  private fun getUserPreferences() =
    UserPreferencesStoreModel()
      .get()
      .orDefault(UserPreferences.DEFAULT_USER_PREFERENCES)

  internal companion object {
    internal const val MAX_RECENT_SEARCHES = 10
  }
}

