package com.saulordz.dictionary.ui.home

import android.os.Bundle
import com.jakewharton.rxbinding3.view.clicks
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivity
import com.saulordz.dictionary.data.model.Definition
import com.saulordz.dictionary.ui.home.recycler.DefinitionAdapter
import com.saulordz.dictionary.utils.extensions.addDefaultVerticalSpacing
import com.saulordz.dictionary.utils.extensions.getStringText
import kotlinx.android.synthetic.main.activity_home.*
import toothpick.Scope
import javax.inject.Inject

class HomeActivity
  : BaseActivity<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

  @Inject
  lateinit var homePresenter: HomePresenter

  private val definitionAdapter by lazy { DefinitionAdapter() }

  override fun addModules(scope: Scope): Scope = scope.installModules(HomeModule())

  override fun createPresenter() = homePresenter

  override val searchTerm: String
    get() = a_home_search_input.getStringText()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    initViews()
  }

  override fun displayError() =
    showError(getString(R.string.search_error_searching, searchTerm))

  override fun displaySearchResult(definitions: List<Definition>?) =
    definitionAdapter.submitList(definitions)

  private fun initViews() {
    presenter.registerSearchButtonObservable(a_home_search_button.clicks())

    a_home_definition_recycler.adapter = definitionAdapter
    a_home_definition_recycler.addDefaultVerticalSpacing()
  }

}
