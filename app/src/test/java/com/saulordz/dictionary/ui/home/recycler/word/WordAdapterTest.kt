package com.saulordz.dictionary.ui.home.recycler.word

import android.widget.FrameLayout
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.Word
import org.junit.Test

class WordAdapterTest : BaseActivityTest() {

  private val mockWord = mock<Word>()
  private val mockViewHolder = mock<WordViewHolder>()

  private val adapter = WordAdapter()

  @Test
  fun testOnCreateViewHolder() {
    val holder = adapter.onCreateViewHolder(FrameLayout(application), 0)

    assertThat(holder).isInstanceOf(WordViewHolder::class.java)
  }

  @Test
  fun testOnBindViewHolder() {
    adapter.submitList(listOf(mockWord))

    adapter.onBindViewHolder(mockViewHolder, 0)

    verify(mockViewHolder).setWord(mockWord)
  }
}