package com.saulordz.dictionary.ui.about

import com.saulordz.dictionary.base.BaseContract

interface AboutContract {

  interface View : BaseContract.View {
    var versionNumber: String?
  }

  interface Presenter : BaseContract.Presenter<View> {
    fun initialize()
  }
}