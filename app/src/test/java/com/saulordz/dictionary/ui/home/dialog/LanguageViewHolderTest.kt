package com.saulordz.dictionary.ui.home.dialog

import android.view.LayoutInflater
import android.widget.FrameLayout
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.model.LanguageSelectionState
import kotlinx.android.synthetic.main.item_language.view.*
import org.junit.Test

class LanguageViewHolderTest : BaseActivityTest() {

  private val mockLanguage = mock<Language> {
    on { languageStringRes } doReturn R.string.app_name
  }
  private val mockLanguageSelectionState = mock<LanguageSelectionState> {
    on { language } doReturn mockLanguage
    on { selected } doReturn true
  }

  private val itemView = LayoutInflater.from(application).inflate(R.layout.item_language, FrameLayout(application), false)

  @Test
  fun testSetLanguageSelectionState() {
    val viewHolder = LanguageViewHolder(itemView) {}
    itemView.i_language_radio.text = ""
    itemView.i_language_radio.isChecked = false

    viewHolder.languageSelectionState = mockLanguageSelectionState

    verify(mockLanguageSelectionState).language
    verify(mockLanguageSelectionState).selected
    verify(mockLanguage).languageStringRes
    verifyNoMoreInteractions(mockLanguage, mockLanguageSelectionState)
    assertThat(itemView.i_language_radio.text).isEqualTo(application.getString(R.string.app_name))
    assertThat(itemView.i_language_radio.isChecked).isTrue()
  }

  @Test
  fun testOnLanguageClickedListener() {
    var wasCalled = false
    val viewHolder = LanguageViewHolder(itemView) { wasCalled = true }
    viewHolder.languageSelectionState = mockLanguageSelectionState

    viewHolder.itemView.i_language_radio.performClick()

    assertThat(wasCalled).isTrue()
  }
}