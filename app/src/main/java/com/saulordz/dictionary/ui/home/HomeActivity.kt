package com.saulordz.dictionary.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.editorActionEvents
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivity
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.ui.home.recycler.word.WordAdapter
import com.saulordz.dictionary.utils.extensions.*
import com.saulordz.dictionary.utils.widgets.MaterialDialogHelper
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_main_navigation_drawer.*
import toothpick.Scope
import javax.inject.Inject

class HomeActivity
  : BaseActivity<HomeContract.View, HomeContract.Presenter>(),
  HomeContract.View {

  @Inject
  lateinit var homePresenter: HomePresenter

  @Inject
  lateinit var inputMethodManager: InputMethodManager

  @VisibleForTesting val wordAdapter by lazy { WordAdapter() }

  override fun addModules(scope: Scope): Scope = scope.installModules(HomeModule())

  override fun createPresenter() = homePresenter

  override val searchTerm: String
    get() = a_home_search_input.getStringText()

  override var words: List<Word>? = null
    set(value) = wordAdapter.submitList(value)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    createDrawerToolbar()

    initViews()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    hideKeyboard()
    return super.onOptionsItemSelected(item)
  }

  override fun displayError() =
    showError(getString(R.string.search_error_searching, searchTerm))

  override fun showProgress() = a_home_spinner.makeVisible()

  override fun hideProgress() = a_home_spinner.makeGone()

  override fun hideKeyboard() = hideInputKeyboard(inputMethodManager)

  override fun showLanguageSelector(selectedLanguage: String, availableLanguages: List<String>) {
    MaterialDialogHelper.showLanguagePickerDialog(this) { _, index, text ->
      presenter.handleNewLanguageSelected(index, text)
    }
  }

  private fun initViews() {
    presenter.registerSearchButtonObservable(a_home_search_button.clicks())
    presenter.registerSearchEditorActionEvent(a_home_search_input.editorActionEvents())

    a_home_word_recycler.adapter = wordAdapter
    a_home_word_recycler.addDefaultVerticalSpacing()
    i_main_navigation.setNavigationItemSelectedListener {
      closeDrawer()
      onNavigationItemSelected(it)
    }
  }

  private fun onLanguageMenuItemSelected(): Boolean {
    presenter.handleLanguageMenuItemSelected()
    return true
  }

  private fun onAboutMenuItemSelected(): Boolean {
    Toast.makeText(this, "Feature not yet implemented", Toast.LENGTH_LONG).show()
    return true
  }

  private fun onFeedbackMenuItemSelected(): Boolean {
    Toast.makeText(this, "Feature not yet implemented", Toast.LENGTH_LONG).show()
    return true
  }

  private fun onRateAppMenuItemSelected(): Boolean {
    Toast.makeText(this, "Feature not yet implemented", Toast.LENGTH_LONG).show()
    return true
  }

  @VisibleForTesting
  fun onNavigationItemSelected(item: MenuItem) =
    when (item.itemId) {
      R.id.m_main_language -> onLanguageMenuItemSelected()
      R.id.m_main_about -> onAboutMenuItemSelected()
      R.id.m_main_feedback -> onFeedbackMenuItemSelected()
      R.id.m_main_rate_app -> onRateAppMenuItemSelected()
      else -> false
    }

}
