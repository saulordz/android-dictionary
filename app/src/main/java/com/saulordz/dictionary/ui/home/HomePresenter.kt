package com.saulordz.dictionary.ui.home

import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.saulordz.dictionary.base.BasePresenter
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.RecentSearch
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.data.repository.GoogleDictionaryRepository
import com.saulordz.dictionary.data.repository.SharedPreferencesRepository
import com.saulordz.dictionary.rx.SchedulerComposer
import com.saulordz.dictionary.ui.home.dialog.LanguageSelectionStateMapper
import com.saulordz.dictionary.utils.extensions.orDefault
import io.reactivex.Observable
import javax.inject.Inject

class HomePresenter @Inject constructor(
  schedulerComposer: SchedulerComposer,
  private val googleDictionaryRepository: GoogleDictionaryRepository,
  private val sharedPreferencesRepository: SharedPreferencesRepository
) : BasePresenter<HomeContract.View>(schedulerComposer, sharedPreferencesRepository), HomeContract.Presenter {

  override fun initialize() {
    loadUserLanguage()
    loadRecentSearches()
  }

  override fun registerSearchEditorActionEvent(observable: Observable<TextViewEditorActionEvent>) = observable.onObservableSearchAction {
    prepareSearch()
  }

  override fun registerSearchButtonObservable(observable: Observable<Unit>) = observable.onObservableAction {
    prepareSearch()
  }

  override fun handleLanguageMenuItemSelected() = ifViewAttached { view ->
    view.showLanguageSelector()
  }

  override fun handleFeedbackMenuItemSelected() = ifViewAttached { view ->
    view.startEmailIntent(FEEDBACK_EMAIL_ADDRESS, FEEDBACK_EMAIL_SUBJECT)
  }

  override fun handleLanguageClicked(clickedLanguage: LanguageSelectionState?) = ifViewAttached { view ->
    if (clickedLanguage != null) {
      view.languageSelectionStates = LanguageSelectionStateMapper(clickedLanguage.language)
    }
  }

  override fun handleNewLanguageApplied() = ifViewAttached { view ->
    val selectedLanguage = view.languageSelectionStates?.find { it.selected }?.language
    if (selectedLanguage != null) {
      updateUserLanguage(selectedLanguage)
    } else {
      view.showLanguageSelectionError()
    }
  }

  override fun handleAboutMenuItemSelected() = ifViewAttached { view ->
    view.startAboutActivity()
  }

  override fun handleRateMenuItemSelected() = ifViewAttached { view ->
    view.startPlayStoreIntent()
  }

  override fun handleRecentSearchClicked(recentSearch: RecentSearch?) {
    val searchTerm = recentSearch?.searchTerm
    val language = recentSearch?.language
    val words = recentSearch?.words
    if (searchTerm != null && language != null && words != null) {
      onSearchSuccess(searchTerm, language, words)
    }
  }

  override fun handleBackPressed() = ifViewAttached { view ->
    when {
      view.isMenuDisplayed -> view.hideMenu()
      view.isDefinitionDisplayed -> showRecentSearches()
      else -> view.exit()
    }
  }

  override fun handleHomePressed() = ifViewAttached { view ->
    when {
      view.isDefinitionDisplayed -> showRecentSearches()
      else -> view.showMenu()
    }
  }

  private fun showRecentSearches() = ifViewAttached { view ->
    view.words = null
    view.showRecentSearches()
  }

  private fun prepareSearch() = ifViewAttached { view ->
    val language = view.languageSelectionStates.getSelectedLanguage()
    val searchTerm = view.searchTerm
    if (searchTerm.isNotBlank()) {
      view.hideKeyboard()
      view.showProgress()
      search(language, searchTerm)
    }
  }

  private fun search(language: Language, searchTerm: String) = addDisposable {
    googleDictionaryRepository.singleSearchWord(language.languageTag, searchTerm)
      .compose(schedulerComposer.singleComposer())
      .subscribe({ onSearchSuccess(searchTerm, language, it) }) { onError(it) }
  }

  private fun onSearchSuccess(searchTerm: String, language: Language, words: List<Word>) {
    saveSearch(searchTerm, language, words)
    handleSearchResults(words)
  }

  private fun saveSearch(searchTerm: String, language: Language, words: List<Word>) = ifViewAttached { view ->
    val recentSearch = RecentSearch(searchTerm, language, words)
    sharedPreferencesRepository.addRecentSearch(recentSearch)
  }

  private fun handleSearchResults(words: List<Word>) = ifViewAttached { view ->
    view.words = words
    view.showBackMenu()
    view.showSearchResults()
    view.hideProgress()
    loadRecentSearches()
  }

  private fun loadRecentSearches() = ifViewAttached { view ->
    view.recentSearches = sharedPreferencesRepository.getRecentSearches()
  }

  private fun loadUserLanguage() = ifViewAttached { view ->
    val userLanguage = sharedPreferencesRepository.getUserPreferredLanguage()
    view.languageSelectionStates = LanguageSelectionStateMapper(userLanguage)
  }

  override fun onError(throwable: Throwable?, message: String) = ifViewAttached { view ->
    view.hideProgress()
    view.showDefinitionNotFoundError()
    super.onError(throwable, message)
  }

  private fun Iterable<LanguageSelectionState>?.getSelectedLanguage() =
    this?.find { it.selected }
      ?.language
      .orDefault(Language.DEFAULT_LANGUAGE)
}