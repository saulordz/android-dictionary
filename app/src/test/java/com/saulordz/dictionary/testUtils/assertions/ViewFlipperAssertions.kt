package com.saulordz.dictionary.testUtils.assertions

import android.view.View
import android.widget.ViewFlipper
import assertk.Assert
import assertk.assertions.isTrue
import com.saulordz.dictionary.utils.extensions.isDisplayed

internal fun Assert<ViewFlipper>.isShowing(view: View) = given { actual ->
  assertThat(actual.isDisplayed(view)).isTrue()
}