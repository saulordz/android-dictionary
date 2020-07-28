package com.saulordz.dictionary.testUtils.assertions

import android.widget.TextView
import androidx.annotation.StringRes
import assertk.Assert
import assertk.assertions.isEqualTo


internal fun Assert<TextView>.hasText(expectedText: String) = given { actual ->
  assertThat(actual.text).isEqualTo(expectedText)
}

internal fun Assert<TextView>.hasText(@StringRes expectedResourceId: Int, vararg formatArgs: Any?) = given { actual ->
  val expectedText = actual.context.getString(expectedResourceId, *formatArgs)
  assertThat(actual).hasText(expectedText)
}

internal fun Assert<TextView>.hasText(@StringRes expectedResourceId: Int, @StringRes expectedArgResId: Int) = given { actual ->
  val expectedFormatArg = actual.context.getString(expectedArgResId)
  val expectedText = actual.context.getString(expectedResourceId, expectedFormatArg)
  assertThat(actual).hasText(expectedText)
}