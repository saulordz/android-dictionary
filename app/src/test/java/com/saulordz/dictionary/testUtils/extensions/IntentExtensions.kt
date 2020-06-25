package com.saulordz.dictionary.testUtils.extensions

import android.content.Intent
import assertk.Assert
import assertk.assertions.isEqualTo


internal fun Assert<Intent>.hasAction(action: String) = given { actual ->
  assertThat(actual.action).isEqualTo(action)
}


internal fun Assert<Intent>.hasData(data: String) = given { actual ->
  assertThat(actual.data.toString()).isEqualTo(data)
}