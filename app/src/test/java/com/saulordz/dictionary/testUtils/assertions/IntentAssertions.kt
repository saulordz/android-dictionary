package com.saulordz.dictionary.testUtils.assertions

import android.content.Intent
import assertk.Assert
import assertk.assertions.isEqualTo


internal fun Assert<Intent>.hasAction(action: String) = given { actual ->
  assertThat(actual.action).isEqualTo(action)
}

internal fun Assert<Intent>.hasData(data: String) = given { actual ->
  assertThat(actual.data.toString()).isEqualTo(data)
}

internal fun Assert<Intent>.hasComponentClass(className: String) = given { actual ->
  assertThat(actual.component?.className).isEqualTo(className)
}