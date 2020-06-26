package com.saulordz.dictionary.ui.about

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.saulordz.dictionary.BuildConfig
import com.saulordz.dictionary.data.repository.SharedPreferencesRepository
import com.saulordz.dictionary.testUtils.RxTestUtils.testSchedulerComposer
import org.junit.Before
import org.junit.Test

class AboutPresenterTest {

  private val mockSharedPreferencesRepository = mock<SharedPreferencesRepository>()
  private val mockView = mock<AboutContract.View>()

  private val presenter = AboutPresenter(testSchedulerComposer, mockSharedPreferencesRepository)

  @Before
  fun setUp() {
    presenter.attachView(mockView)
  }

  @Test
  fun testInitialize() {
    presenter.initialize()

    verify(mockView).versionNumber = BuildConfig.VERSION_NAME
  }

}