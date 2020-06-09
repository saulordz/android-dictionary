package com.saulordz.dictionary.ui.home.recycler

import android.view.LayoutInflater
import android.widget.FrameLayout
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.Definition
import kotlinx.android.synthetic.main.item_definition.view.*
import org.junit.Test

class DefinitionViewHolderTest : BaseActivityTest() {

  private val mockDefinition = mock<Definition>{
    on { definition }doReturn TEST_DEFINITION
  }
  private val itemView = LayoutInflater.from(application).inflate(R.layout.item_definition, FrameLayout(application), false)
  private val viewHolder = DefinitionViewHolder(itemView)

  @Test
  fun testSetDefinition() {
    itemView.i_definition_text.text = ""

    viewHolder.setDefinition(mockDefinition)

    assertThat(itemView.i_definition_text.text).isEqualTo(TEST_DEFINITION)
  }

  private companion object{
    private const val TEST_DEFINITION = "testdef"
  }
}