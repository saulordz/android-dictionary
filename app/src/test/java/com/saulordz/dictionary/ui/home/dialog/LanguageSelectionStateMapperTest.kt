package com.saulordz.dictionary.ui.home.dialog

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.argThat
import com.saulordz.dictionary.data.model.Language
import org.junit.Test

class LanguageSelectionStateMapperTest {

  @Test
  fun testInvoke() {
    val selectedLanguage = Language.ENGLISH

    val actual = LanguageSelectionStateMapper(selectedLanguage)

    assertThat(actual.size).isEqualTo(Language.values().size)
    assertThat(actual.contains(argThat {
      selected && language == Language.ENGLISH
    }))
    assertThat(actual.count { it.selected }).isEqualTo(1)
  }

  @Test
  fun testInvokeWithNullSelectedLanguage() {
    val actual = LanguageSelectionStateMapper(null)

    assertThat(actual.size).isEqualTo(Language.values().size)
    assertThat(actual.contains(argThat {
      selected && language == Language.values().first()
    }))
    assertThat(actual.count { it.selected }).isEqualTo(1)
  }

}