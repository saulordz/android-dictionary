package com.saulordz.dictionary.data.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test

class WordTest {

  private val mockDefinitionOne = mock<Definition> {
    on { definition } doReturn TEST_DEFINITION_ONE
  }
  private val mockDefinitionTwo = mock<Definition> {
    on { definition } doReturn TEST_DEFINITION_TWO
  }

  @Test
  fun testGetFormattedWord() {
    val originalString = "!hello!"
    val expectedString = "Hello"
    val word = Word(word = originalString)

    val actual = word.formattedWord

    assertThat(actual).isEqualTo(expectedString)
  }

  @Test
  fun testGetDefinitions() {
    val word = Word(
      meanings = mapOf(
        TEST_CATEGORY_ONE to listOf(mockDefinitionOne),
        TEST_CATEGORY_TWO to listOf(mockDefinitionTwo)
      )
    )
    val expectedDefinitions = listOf(mockDefinitionOne, mockDefinitionTwo)

    val actualDefinitions = word.definitions

    assertThat(actualDefinitions).isEqualTo(expectedDefinitions)
  }

  private companion object {
    private const val TEST_DEFINITION_ONE = "1234definition"
    private const val TEST_DEFINITION_TWO = "definitisagoon"
    private const val TEST_CATEGORY_ONE = "kategory"
    private const val TEST_CATEGORY_TWO = "categori"
  }
}