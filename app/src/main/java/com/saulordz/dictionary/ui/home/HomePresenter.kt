package com.saulordz.dictionary.ui.home

import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.saulordz.dictionary.base.BasePresenter
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.data.repository.GoogleDictionaryRepository
import com.saulordz.dictionary.rx.SchedulerComposer
import com.saulordz.dictionary.ui.home.dialog.LanguageSelectionStateMapper
import io.reactivex.Observable
import javax.inject.Inject

class HomePresenter @Inject constructor(
  schedulerComposer: SchedulerComposer,
  private val googleDictionaryRepository: GoogleDictionaryRepository
) : BasePresenter<HomeContract.View>(schedulerComposer), HomeContract.Presenter {

  override fun initialize() = ifViewAttached { view ->
    view.languageSelectionStates = LanguageSelectionStateMapper(null)
  }

  override fun registerSearchEditorActionEvent(observable: Observable<TextViewEditorActionEvent>) = observable.onObservableSearchAction {
    prepareSearch()
  }

  override fun registerSearchButtonObservable(observable: Observable<Unit>) = observable.onObservableAction { view ->
    prepareSearch()
  }

  override fun handleLanguageMenuItemSelected() = ifViewAttached { view ->
    view.showLanguageSelector()
  }

  override fun handleLanguageClicked(clickedLanguage: LanguageSelectionState?) = ifViewAttached { view ->
    if (clickedLanguage != null) {
      view.languageSelectionStates = LanguageSelectionStateMapper(clickedLanguage.language)
    }
  }

  override fun handleNewLanguageApplied() = ifViewAttached { view ->
    val selectedLanguage = view.languageSelectionStates?.find { it.selected }
    selectedLanguage?.let { view.applyNewLanguage(it.language) }
  }

  private fun prepareSearch() = ifViewAttached { view ->
    val searchTerm = view.searchTerm
    if (searchTerm.isNotBlank()) {
      view.hideKeyboard()
      view.showProgress()
      search(searchTerm)
    }
  }

  private fun search(searchTerm: String) = addDisposable {
    googleDictionaryRepository.singleSearchWord(searchTerm)
      .compose(schedulerComposer.singleComposer())
      .subscribe(::onSearchSuccess) { onError(it) }
  }

  override fun onError(throwable: Throwable?, message: String) = ifViewAttached { view ->
    super.onError(throwable, message)
    view.hideProgress()
    view.displayError()
  }

  private fun onSearchSuccess(words: List<Word>) = ifViewAttached { view ->
    view.hideProgress()
    view.words = words
  }
}