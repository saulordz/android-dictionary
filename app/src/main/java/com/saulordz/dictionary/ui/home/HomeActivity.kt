package com.saulordz.dictionary.ui.home

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.jakewharton.rxbinding3.view.clicks
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivity
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.ui.home.recycler.word.WordAdapter
import com.saulordz.dictionary.utils.extensions.addDefaultVerticalSpacing
import com.saulordz.dictionary.utils.extensions.getStringText
import com.saulordz.dictionary.utils.extensions.makeGone
import com.saulordz.dictionary.utils.extensions.makeVisible
import kotlinx.android.synthetic.main.activity_home.*
import toothpick.Scope
import javax.inject.Inject

class HomeActivity
  : BaseActivity<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

  @Inject
  lateinit var homePresenter: HomePresenter

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

    initViews()
  }

  override fun displayError() =
    showError(getString(R.string.search_error_searching, searchTerm))

  override fun showProgress() = a_home_spinner.makeVisible()

  override fun hideProgress() = a_home_spinner.makeGone()

  private fun initViews() {
    presenter.registerSearchButtonObservable(a_home_search_button.clicks())

    a_home_word_recycler.adapter = wordAdapter
    a_home_word_recycler.addDefaultVerticalSpacing()
  }

}
