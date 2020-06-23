package com.saulordz.dictionary.data.model

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import com.saulordz.dictionary.R
import com.saulordz.dictionary.utils.extensions.orDefault
import java.util.*

enum class Language(@StringRes val languageStringRes: Int, val iso3Language: String) {
  SPANISH(R.string.message_spanish, "spa"),
  ENGLISH(R.string.message_english, "eng");

  internal companion object {
    @SuppressLint("ConstantLocale")
    internal val DEFAULT_LANGUAGE = fromIso3Language(Locale.getDefault().isO3Language)

    private fun fromIso3Language(iso3Language: String) =
      values()
        .find { it.iso3Language == iso3Language }
        .orDefault(ENGLISH)
  }
}