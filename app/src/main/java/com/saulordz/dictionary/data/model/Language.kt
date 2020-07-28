package com.saulordz.dictionary.data.model

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import com.saulordz.dictionary.R
import com.saulordz.dictionary.utils.extensions.orDefault
import java.util.*

enum class Language(@StringRes val languageStringRes: Int, val languageTag: String) {
  ENGLISH(R.string.message_english, "en"),
  ITALIAN(R.string.message_italian, "it"),
  SPANISH(R.string.message_spanish, "es");

  internal companion object {
    @SuppressLint("ConstantLocale")
    internal val DEFAULT_LANGUAGE = fromIso3Language(Locale.getDefault().isO3Language)

    //TODO update function to use languageTag
    private fun fromIso3Language(iso3Language: String) =
      values()
        .find { it.languageTag == iso3Language }
        .orDefault(ENGLISH)
  }
}