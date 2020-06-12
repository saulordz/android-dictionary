package com.saulordz.dictionary.ui.home.recycler.word

import android.view.LayoutInflater
import android.widget.FrameLayout
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.Definition
import com.saulordz.dictionary.data.model.Word
import kotlinx.android.synthetic.main.item_word.view.*
import org.junit.Test

class WordViewHolderTest : BaseActivityTest() {

  private val mockDefinition = mock<Definition>()
  private val mockWord = mock<Word> {
    on { formattedWord } doReturn TEST_WORD
    on { definitions } doReturn listOf(mockDefinition)
  }
  private val itemView = LayoutInflater.from(application).inflate(R.layout.item_word, FrameLayout(application), false)

  private val viewHolder = WordViewHolder(itemView)

  @Test
  fun testSetDefinition() {
    itemView.i_word_text.text = ""
    viewHolder.definitionAdapter.submitList(null)

    viewHolder.setWord(mockWord)

    verify(mockWord).formattedWord
    verify(mockWord).definitions
    assertThat(itemView.i_word_text.text).isEqualTo(TEST_WORD)
    assertThat(viewHolder.definitionAdapter.itemCount).isEqualTo(1)
    verifyNoMoreInteractions(mockWord)
  }

  private companion object {
    private const val TEST_WORD = "testdef"
  }
}