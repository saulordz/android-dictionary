package com.saulordz.dictionary.ui.home.dialog

import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
import com.saulordz.dictionary.utils.extensions.orDefault

object LanguageSelectionStateMapper : Function1<Language?, List<LanguageSelectionState>> {

  override fun invoke(selectedLanguage: Language?): List<LanguageSelectionState> {
    val availableLanguages = Language.values()
    val languageToSelect = selectedLanguage.orDefault(availableLanguages.first())
    return availableLanguages.map { LanguageSelectionState(it, it == languageToSelect) }
  }
}