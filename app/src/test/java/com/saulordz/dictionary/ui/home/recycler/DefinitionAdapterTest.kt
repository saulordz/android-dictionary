package com.saulordz.dictionary.ui.home.recycler

import android.widget.FrameLayout
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.Definition
import org.junit.Test

class DefinitionAdapterTest : BaseActivityTest() {

  private val mockDefinition = mock<Definition>()
  private val mockViewHolder = mock<DefinitionViewHolder>()

  private val adapter = DefinitionAdapter()

  @Test
  fun testOnCreateViewHolder() {
    val holder = adapter.onCreateViewHolder(FrameLayout(application), 0)

    assertThat(holder).isInstanceOf(DefinitionViewHolder::class)
  }

  @Test
  fun testOnBindViewHolder() {
    adapter.submitList(listOf(mockDefinition))

    adapter.onBindViewHolder(mockViewHolder, 0)

    verify(mockViewHolder).setDefinition(mockDefinition)
  }
}