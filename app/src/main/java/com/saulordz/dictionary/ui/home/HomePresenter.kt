package com.saulordz.dictionary.ui.home

import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.saulordz.dictionary.base.BasePresenter
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
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

  override fun initialize() = ifViewAttached { view ->
    val userLanguage = sharedPreferencesRepository.getUserPreferredLanguage()
    view.languageSelectionStates = LanguageSelectionStateMapper(userLanguage)
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

  private fun prepareSearch() = ifViewAttached { view ->
    val language = view.languageSelectionStates.getSelectedLanguage().languageTag
    val searchTerm = view.searchTerm
    if (searchTerm.isNotBlank()) {
      view.hideKeyboard()
      view.showProgress()
      search(language, searchTerm)
    }
  }

  private fun search(language: String, searchTerm: String) = addDisposable {
    googleDictionaryRepository.singleSearchWord(language, searchTerm)
      .compose(schedulerComposer.singleComposer())
      .subscribe(::onSearchSuccess) { onError(it) }
  }

  override fun onError(throwable: Throwable?, message: String) = ifViewAttached { view ->
    super.onError(throwable, message)
    view.hideProgress()
    view.showDefinitionNotFoundError()
  }

  private fun onSearchSuccess(words: List<Word>) = ifViewAttached { view ->
    view.hideProgress()
    view.words = words
  }

  private fun Iterable<LanguageSelectionState>?.getSelectedLanguage() =
    this?.find { it.selected }
      ?.language
      .orDefault(Language.DEFAULT_LANGUAGE)
}