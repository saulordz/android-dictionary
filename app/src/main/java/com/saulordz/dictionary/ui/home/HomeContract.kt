package com.saulordz.dictionary.ui.home

import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.saulordz.dictionary.base.BaseContract
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.Word
import io.reactivex.Observable

interface HomeContract {

  interface View : BaseContract.View {
    val searchTerm: String

    var words: List<Word>?
    var languageSelectionStates: List<LanguageSelectionState>?

    fun showDefinitionNotFoundError()
    fun showLanguageSelectionError()
    fun showProgress()
    fun hideProgress()
    fun hideKeyboard()
    fun showLanguageSelector()
  }

  interface Presenter : BaseContract.Presenter<View> {
    fun initialize()
    fun registerSearchButtonObservable(observable: Observable<Unit>)
    fun registerSearchEditorActionEvent(observable: Observable<TextViewEditorActionEvent>)
    fun handleLanguageMenuItemSelected()
    fun handleLanguageClicked(clickedLanguage: LanguageSelectionState?)
    fun handleNewLanguageApplied()
  }
}