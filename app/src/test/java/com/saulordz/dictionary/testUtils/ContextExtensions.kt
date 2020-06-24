package com.saulordz.dictionary.testUtils

import android.content.Context
import assertk.Assert
import assertk.assertions.isEqualTo
import java.util.*

internal fun Assert<Context?>.hasSameLocales(contextToCompare: Context) = given { actual ->
  assertThat(actual?.resources?.configuration?.locales).isEqualTo(contextToCompare.resources.configuration.locales)
}

internal fun Assert<Context?>.hasLocale(locale: Locale) = given { actual ->
  assertThat(actual?.resources?.configuration?.locales?.get(0)).isEqualTo(locale)
}