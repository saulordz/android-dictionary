package com.saulordz.dictionary.ui.home

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.saulordz.dictionary.base.BaseActivityTest
import org.junit.Test
import org.mockito.Mock


class HomeActivityTest : BaseActivityTest() {

  @Mock lateinit var mockHomePresenter: HomePresenter

  @Test
  fun testOnCreateInteractions() = letActivity<HomeActivity> {
    verify(mockHomePresenter).attachView(it)
    verify(mockHomePresenter).registerSearchButtonObservable(any())
    verifyNoMoreInteractions(mockHomePresenter)
  }

}