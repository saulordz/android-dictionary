package com.saulordz.dictionary.ui.about

import assertk.assertThat
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.testUtils.assertions.hasText
import com.saulordz.dictionary.testUtils.assertions.isGone
import com.saulordz.dictionary.testUtils.assertions.isVisible
import com.saulordz.dictionary.utils.extensions.makeGone
import com.saulordz.dictionary.utils.extensions.makeVisible
import kotlinx.android.synthetic.main.activity_about.*
import org.junit.Test
import org.mockito.Mock

class AboutActivityTest : BaseActivityTest() {

  @Mock lateinit var mockPresenter: AboutPresenter

  @Test
  fun testOnCreateInteractions() = letActivity<AboutActivity> {
    verify(mockPresenter).attachView(it)
    verify(mockPresenter).initialize()
    verifyNoMoreInteractions(mockPresenter)
  }

  @Test
  fun testVersionNumber() = letActivity<AboutActivity> {
    it.a_about_version.text = ""
    it.a_about_version.makeGone()

    it.versionNumber = TEST_VERSION

    assertThat(it.a_about_version).hasText(R.string.message_version, TEST_VERSION)
    assertThat(it.a_about_version).isVisible()
  }

  @Test
  fun testVersionNumberWithNullVersion() = letActivity<AboutActivity> {
    it.a_about_version.makeVisible()

    it.versionNumber = null

    assertThat(it.a_about_version).isGone()
  }


  private companion object {
    private const val TEST_VERSION = "12.8.0"
  }
}