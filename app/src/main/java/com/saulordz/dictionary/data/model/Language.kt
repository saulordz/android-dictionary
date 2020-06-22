package com.saulordz.dictionary.data.model

import androidx.annotation.StringRes
import com.saulordz.dictionary.R

enum class Language(@StringRes val languageStringRes: Int) {
  SPANISH(R.string.message_spanish),
  ENGLISH(R.string.message_english)
}