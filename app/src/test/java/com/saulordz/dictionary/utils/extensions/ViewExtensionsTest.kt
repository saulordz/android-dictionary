package com.saulordz.dictionary.utils.extensions

import android.view.View
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.saulordz.dictionary.base.BaseActivityTest
import org.junit.Test

class ViewExtensionsTest : BaseActivityTest() {

  private val view = View(application)

  @Test
  fun testMakeVisible() {
    view.visibility = View.GONE

    view.makeVisible()

    assertThat(view.visibility).isEqualTo(View.VISIBLE)
  }


  @Test
  fun testMakeGone() {
    view.visibility = View.VISIBLE

    view.makeGone()

    assertThat(view.visibility).isEqualTo(View.GONE)
  }
}