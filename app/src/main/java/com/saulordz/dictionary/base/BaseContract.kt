package com.saulordz.dictionary.base

import android.content.Context
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseContract {

  interface View : MvpView {
    fun recreateView()
  }

  interface Presenter<V : View> : MvpPresenter<V> {
    fun createCustomContext(context: Context?): Context?
  }
}