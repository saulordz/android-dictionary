package com.saulordz.dictionary.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.annotation.VisibleForTesting
import androidx.core.view.GravityCompat
import com.afollestad.materialdialogs.DialogCallback
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.editorActionEvents
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivity
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.data.model.RecentSearch
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.ui.about.AboutActivity
import com.saulordz.dictionary.ui.home.dialog.LanguageDialogAdapter
import com.saulordz.dictionary.ui.home.dialog.OnLanguageClickedListener
import com.saulordz.dictionary.ui.home.recycler.recentsearch.OnRecentSearchClicked
import com.saulordz.dictionary.ui.home.recycler.recentsearch.RecentSearchAdapter
import com.saulordz.dictionary.ui.home.recycler.word.WordAdapter
import com.saulordz.dictionary.utils.extensions.*
import com.saulordz.dictionary.utils.helpers.MaterialDialogHelper
import de.cketti.mailto.EmailIntentBuilder
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

  override fun addModules(scope: Scope): Scope = scope.installModules(HomeModule())

  override fun createPresenter() = homePresenter

  @VisibleForTesting val wordAdapter by lazy { WordAdapter() }

  @VisibleForTesting val recentSearchAdapter by lazy { RecentSearchAdapter(onRecentSearchClicked) }

  @VisibleForTesting val languageAdapter by lazy { LanguageDialogAdapter(onLanguageClickedListener) }

  @VisibleForTesting val onRecentSearchClicked: OnRecentSearchClicked = { presenter.handleRecentSearchClicked(it) }

  @VisibleForTesting val onLanguageClickedListener: OnLanguageClickedListener = { presenter.handleLanguageClicked(it) }

  @VisibleForTesting val onNavigationItemSelectedListener: ((MenuItem) -> Boolean) = {
    onNavigationItemSelected(it)
    closeDrawer()
    true
  }

  @VisibleForTesting val onApplyLanguageListener: DialogCallback = { _ ->
    presenter.handleNewLanguageApplied()
  }

  override val searchTerm: String
    get() = a_home_search_input.getStringText()

  override val isDefinitionDisplayed: Boolean
    get() = a_home_view_flipper.isDisplayed(a_home_word_recycler)

  override val isMenuDisplayed: Boolean
    get() = isDrawerOpen

  override var words: List<Word>? = null
    set(value) {
      a_home_word_recycler.removeAllViews()
      wordAdapter.submitList(value)
    }

  override var recentSearches: List<RecentSearch>? = null
    set(value) = recentSearchAdapter.submitList(value)

  override var languageSelectionStates: List<LanguageSelectionState>? = null
    set(value) {
      field = value
      languageAdapter.submitList(value)
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    createDrawerToolbar()

    initViews()
    presenter.initialize()
  }

  override fun onBackPressed() = presenter.handleBackPressed()

  override fun onHomePressed() = presenter.handleHomePressed()

  override fun showDefinitionNotFoundError() =
    showError(getString(R.string.word_not_found_error, searchTerm))

  override fun showLanguageSelectionError() =
    showError(getString(R.string.word_not_found_error))

  override fun showProgress() = a_home_spinner.makeVisible()

  override fun hideProgress() = a_home_spinner.makeGone()

  override fun hideKeyboard() = hideInputKeyboard(inputMethodManager)

  override fun showLanguageSelector() =
    MaterialDialogHelper.showLanguagePickerDialog(this, onApplyLanguageListener, languageAdapter)

  override fun startEmailIntent(recipient: String, subject: String) {
    EmailIntentBuilder.from(this)
      .to(recipient)
      .subject(subject)
      .start()
  }

  override fun startAboutActivity() {
    val intent = Intent(this, AboutActivity::class.java)
    startActivity(intent)
  }

  override fun startPlayStoreIntent() {
    val uri = Uri.parse(PLAY_STORE_BASE_URL + packageName)
    val playStoreIntent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(playStoreIntent)
  }

  override fun showRecentSearches() {
    createDrawerToolbar()
    a_home_view_flipper.showView(a_home_recent_search_recycler)
  }

  override fun showSearchResults() =
    a_home_view_flipper.showView(a_home_word_recycler)

  override fun showBackMenu() = createBackToolbar()

  override fun hideMenu() = closeDrawer()

  override fun exit() = finish()

  override fun showMenu() {
    hideKeyboard()
    a_base_drawer.openDrawer(GravityCompat.START)
  }

  private fun initViews() {
    presenter.registerSearchButtonObservable(a_home_search_button.clicks())
    presenter.registerSearchEditorActionEvent(a_home_search_input.editorActionEvents())

    i_main_navigation.setNavigationItemSelectedListener(onNavigationItemSelectedListener)

    a_home_word_recycler.adapter = wordAdapter
    a_home_word_recycler.addDefaultVerticalSpacing()

    a_home_recent_search_recycler.adapter = recentSearchAdapter

    a_home_view_flipper.setInAnimation(this, android.R.anim.fade_in)
  }

  private fun onNavigationItemSelected(item: MenuItem) =
    when (item.itemId) {
      R.id.m_main_language -> presenter.handleLanguageMenuItemSelected()
      R.id.m_main_about -> presenter.handleAboutMenuItemSelected()
      R.id.m_main_feedback -> presenter.handleFeedbackMenuItemSelected()
      R.id.m_main_rate_app -> presenter.handleRateMenuItemSelected()
      else -> Unit
    }

  internal companion object {
    internal const val PLAY_STORE_BASE_URL = "market://details?id="
  }
}
