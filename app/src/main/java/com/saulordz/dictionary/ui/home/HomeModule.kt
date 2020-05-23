package com.saulordz.dictionary.ui.home

import toothpick.config.Module

class HomeModule : Module() {

  init {
    bind(HomeContract.Presenter::class.java).to(HomePresenter::class.java)
  }
}