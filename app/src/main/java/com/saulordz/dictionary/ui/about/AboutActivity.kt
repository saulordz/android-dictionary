package com.saulordz.dictionary.ui.about

import android.os.Bundle
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivity
import com.saulordz.dictionary.utils.extensions.setTextAndVisibility
import kotlinx.android.synthetic.main.activity_about.*
import toothpick.Scope
import javax.inject.Inject

class AboutActivity
  : BaseActivity<AboutContract.View, AboutContract.Presenter>(),
  AboutContract.View {

  @Inject
  lateinit var aboutPresenter: AboutPresenter

  override var versionNumber: String? = null
    set(value) = a_about_version.setTextAndVisibility(R.string.message_version, value)

  override fun addModules(scope: Scope): Scope = scope.installModules(AboutModule())

  override fun createPresenter() = aboutPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_about)

    initViews()
  }

  private fun initViews() {
    presenter.initialize()
  }
}