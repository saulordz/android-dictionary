package com.saulordz.dictionary.ui.home

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.saulordz.dictionary.data.model.Definition
import io.reactivex.Observable

interface HomeContract {

  interface View : MvpView {
    val searchTerm: String

    var definitions: List<Definition>?
    var word: String?

    fun displayError()
  }

  interface Presenter : MvpPresenter<View> {
    fun registerSearchButtonObservable(observable: Observable<Unit>)
  }
}