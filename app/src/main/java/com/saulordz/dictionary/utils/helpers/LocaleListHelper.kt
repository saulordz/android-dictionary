package com.saulordz.dictionary.utils.helpers

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import com.saulordz.dictionary.utils.extensions.orDefault
import java.util.*

object LocaleListHelper {

  fun createConfigurationContext(context: Context?, userLanguage: String): Context? {
    val deviceLocales = LocaleList.getAdjustedDefault().toList()
    val userLocale = Locale.forLanguageTag(userLanguage)
    val customLocales = (arrayOf(userLocale) + deviceLocales).filterNotNull().distinct()
    val customLocaleList = LocaleList(*customLocales.toTypedArray())

    return context
      ?.resources
      ?.configuration
      ?.setLocaleList(customLocaleList)
      ?.let { context.createConfigurationContext(it) }
      .orDefault(context)
  }

  private fun LocaleList.toList(): List<Locale> {
    val locales = mutableListOf<Locale>()
    for (i in 0 until size()) {
      locales += this.get(i)
    }
    return locales
  }

  private fun Configuration?.setLocaleList(localeList: LocaleList): Configuration? {
    this?.setLocales(localeList)
    return this
  }
}