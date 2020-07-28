package com.saulordz.dictionary.ui.home

import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.saulordz.dictionary.base.BaseContract
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.RecentSearch
import com.saulordz.dictionary.data.model.Word
import io.reactivex.Observable

interface HomeContract {

  interface View : BaseContract.View {
    val searchTerm: String
    val isDefinitionDisplayed: Boolean
    val isMenuDisplayed: Boolean

    var words: List<Word>?
    var recentSearches: List<RecentSearch>?
    var languageSelectionStates: List<LanguageSelectionState>?

    fun exit()
    fun showSearchResults()
    fun showRecentSearches()
    fun showDefinitionNotFoundError()
    fun showLanguageSelectionError()
    fun showLanguageSelector()
    fun showBackMenu()
    fun showMenu()
    fun showProgress()
    fun hideProgress()
    fun hideKeyboard()
    fun hideMenu()
    fun startEmailIntent(recipient: String, subject: String)
    fun startAboutActivity()
    fun startPlayStoreIntent()
  }

  interface Presenter : BaseContract.Presenter<View> {
    fun initialize()
    fun registerSearchButtonObservable(observable: Observable<Unit>)
    fun registerSearchEditorActionEvent(observable: Observable<TextViewEditorActionEvent>)
    fun handleLanguageClicked(clickedLanguage: LanguageSelectionState?)
    fun handleNewLanguageApplied()
    fun handleLanguageMenuItemSelected()
    fun handleAboutMenuItemSelected()
    fun handleFeedbackMenuItemSelected()
    fun handleRateMenuItemSelected()
    fun handleRecentSearchClicked(recentSearch: RecentSearch?)
    fun handleBackPressed()
    fun handleHomePressed()
  }
}