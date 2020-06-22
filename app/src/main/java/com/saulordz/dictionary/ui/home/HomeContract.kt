package com.saulordz.dictionary.ui.home

import com.afollestad.materialdialogs.MaterialDialog
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.Word
import io.reactivex.Observable

interface HomeContract {

  interface View : MvpView {
    val searchTerm: String

    var words: List<Word>?
    var languageSelectionStates: List<LanguageSelectionState>?

    fun displayError()
    fun showProgress()
    fun hideProgress()
    fun hideKeyboard()
    fun showLanguageSelector()
    fun applyNewLanguage(newLanguage: Language)
  }

  interface Presenter : MvpPresenter<View> {
    fun initialize()
    fun registerSearchButtonObservable(observable: Observable<Unit>)
    fun registerSearchEditorActionEvent(editorActionEvent: Observable<TextViewEditorActionEvent>)
    fun handleLanguageMenuItemSelected()
    fun handleLanguageClicked(clickedLanguage: LanguageSelectionState?)
    fun handleNewLanguageApplied()
  }
}