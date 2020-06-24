package com.saulordz.dictionary.utils.helpers

import assertk.assertThat
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.testUtils.hasLocale
import com.saulordz.dictionary.testUtils.hasSameLocales
import org.junit.Test
import java.util.*

class LocaleListHelperTest : BaseActivityTest() {

  @Test
  fun testCreateConfigurationContextWithValidLanguage() {
    val userLanguage = "fr"

    val newContext = LocaleListHelper.createConfigurationContext(application.applicationContext, userLanguage)

    assertThat(newContext).hasLocale(Locale.FRENCH)
  }

  @Test
  fun testCreateConfigurationContextWithInvalidLanguage() {
    val userLanguage = "qlasjfpq"

    val newContext = LocaleListHelper.createConfigurationContext(application.applicationContext, userLanguage)

    assertThat(newContext).hasSameLocales(application.applicationContext)
  }
}