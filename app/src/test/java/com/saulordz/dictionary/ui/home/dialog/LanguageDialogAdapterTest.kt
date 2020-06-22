package com.saulordz.dictionary.ui.home.dialog

import android.widget.FrameLayout
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.LanguageSelectionState
import org.junit.Test

class LanguageDialogAdapterTest : BaseActivityTest() {

  private val mockLanguageSelectionState = mock<LanguageSelectionState>()
  private val mockViewHolder = mock<LanguageViewHolder>()

  private val adapter = LanguageDialogAdapter {}

  @Test
  fun testOnCreateViewHolder() {
    val holder = adapter.onCreateViewHolder(FrameLayout(application), 0)

    assertThat(holder).isInstanceOf(LanguageViewHolder::class)
  }

  @Test
  fun testOnBindViewHolder() {
    adapter.submitList(listOf(mockLanguageSelectionState))

    adapter.onBindViewHolder(mockViewHolder, 0)

    verify(mockViewHolder).languageSelectionState = mockLanguageSelectionState
  }
}