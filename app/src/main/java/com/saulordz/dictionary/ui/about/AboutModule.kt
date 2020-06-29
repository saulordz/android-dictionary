package com.saulordz.dictionary.ui.about

import toothpick.config.Module

class AboutModule : Module() {

  init {
    bind(AboutContract.Presenter::class.java).to(AboutPresenter::class.java)
  }
}