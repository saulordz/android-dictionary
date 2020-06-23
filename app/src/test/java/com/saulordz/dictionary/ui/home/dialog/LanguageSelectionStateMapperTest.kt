package com.saulordz.dictionary.ui.home.dialog

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
import org.junit.Test

class LanguageSelectionStateMapperTest {

  @Test
  fun testInvoke() {
    val selectedLanguage = Language.ENGLISH

    val actual = LanguageSelectionStateMapper(selectedLanguage)

    assertThat(actual.size).isEqualTo(Language.values().size)
    assertThat(actual.count { it.selected }).isEqualTo(1)
    assertThat(actual).contains(LanguageSelectionState(Language.ENGLISH, true))
  }

  @Test
  fun testInvokeWithNullSelectedLanguage() {
    val actual = LanguageSelectionStateMapper(null)

    assertThat(actual.size).isEqualTo(Language.values().size)
    assertThat(actual.count { it.selected }).isEqualTo(1)
    assertThat(actual).contains(LanguageSelectionState(Language.DEFAULT_LANGUAGE, true))
  }

}