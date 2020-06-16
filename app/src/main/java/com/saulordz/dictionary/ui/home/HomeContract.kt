package com.saulordz.dictionary.ui.home

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.saulordz.dictionary.data.model.Word
import io.reactivex.Observable

interface HomeContract {

  interface View : MvpView {
    val searchTerm: String

    var words: List<Word>?

    fun displayError()
    fun showProgress()
    fun hideProgress()
  }

  interface Presenter : MvpPresenter<View> {
    fun registerSearchButtonObservable(observable: Observable<Unit>)
  }
}