package com.saulordz.dictionary.testUtils.assertions

import android.view.View
import assertk.Assert
import assertk.assertions.isEqualTo

internal fun Assert<View>.isVisible() = given { actual ->
  assertThat(actual.visibility).isEqualTo(View.VISIBLE)
}

internal fun Assert<View>.isGone() = given { actual ->
  assertThat(actual.visibility).isEqualTo(View.GONE)
}