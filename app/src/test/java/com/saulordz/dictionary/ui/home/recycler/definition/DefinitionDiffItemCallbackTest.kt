package com.saulordz.dictionary.ui.home.recycler.definition

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.saulordz.dictionary.data.model.Definition
import org.junit.Test

class DefinitionDiffItemCallbackTest {

  private val callback = DefinitionDiffItemCallback()

  @Test
  fun testAreItemsTheSameWithSameItem() {
    val testItem = Definition(definition = TEST_DEFINITION_ONE)

    val actual = callback.areItemsTheSame(testItem, testItem)

    assertThat(actual).isTrue()
  }

  @Test
  fun testAreItemsTheSameWithDifferentItems() {
    val testItemOne = Definition(definition = TEST_DEFINITION_ONE)
    val testItemTwo = Definition(definition = TEST_DEFINITION_TWO)

    val actual = callback.areItemsTheSame(testItemOne, testItemTwo)

    assertThat(actual).isFalse()
  }

  @Test
  fun testAreContentsTheSameWithSameContents() {
    val testItem = Definition(definition = TEST_DEFINITION_ONE)

    val actual = callback.areContentsTheSame(testItem, testItem)

    assertThat(actual).isTrue()
  }

  @Test
  fun testAreContentsTheSameWithDifferentContents() {
    val testItemOne = Definition(definition = TEST_DEFINITION_ONE, examples = TEST_EXAMPLES_ONE)
    val testItemTwo = Definition(definition = TEST_DEFINITION_TWO, examples = TEST_EXAMPLES_TWO)

    val actual = callback.areContentsTheSame(testItemOne, testItemTwo)

    assertThat(actual).isFalse()
  }

  private companion object {
    private const val TEST_DEFINITION_ONE = "definicionuno"
    private const val TEST_DEFINITION_TWO = "definiciondos"
    private const val TEST_EXAMPLES_ONE = "ejemplosUno"
    private const val TEST_EXAMPLES_TWO = "ejemplosDos"
  }
}