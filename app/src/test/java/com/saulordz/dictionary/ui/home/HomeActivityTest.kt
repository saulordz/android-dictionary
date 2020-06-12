package com.saulordz.dictionary.ui.home

import android.view.View
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.data.model.Word
import kotlinx.android.synthetic.main.activity_home.*
import org.junit.Test
import org.mockito.Mock


class HomeActivityTest : BaseActivityTest() {

  private val mockWord = mock<Word>()

  @Mock lateinit var mockHomePresenter: HomePresenter

  @Test
  fun testOnCreateInteractions() = letActivity<HomeActivity> {
    verify(mockHomePresenter).attachView(it)
    verify(mockHomePresenter).registerSearchButtonObservable(any())
    verifyNoMoreInteractions(mockHomePresenter)
  }

  @Test
  fun testGetSearchTerm() = letActivity<HomeActivity> {
    it.a_home_search_input.setText(TEST_SEARCH_TERM)

    val actual = it.searchTerm

    assertThat(actual).isEqualTo(TEST_SEARCH_TERM)
  }

  @Test
  fun testSetWords() = letActivity<HomeActivity> {
    it.words = null

    it.words = listOf(mockWord)

    assertThat(it.wordAdapter.itemCount).isEqualTo(1)
  }

//  @Test
//  fun testSetWordWithNonNull() = letActivity<HomeActivity> {
//    it.word = null
//    it.a_home_word.text = null
//    it.a_home_word.makeGone()
//
//    it.word = TEST_WORD
//
//    assertThat(it.a_home_word.visibility).isEqualTo(View.VISIBLE)
//    assertThat(it.a_home_word.text).isEqualTo(TEST_WORD)
//  }
//
//  @Test
//  fun testSetWordWithNullWord() = letActivity<HomeActivity> {
//    it.a_home_word.text = TEST_WORD
//    it.a_home_word.makeVisible()
//
//    it.word = null
//
//    assertThat(it.a_home_word.text).isEqualTo(TEST_WORD)
//    assertThat(it.a_home_word.visibility).isEqualTo(View.GONE)
//  }


  private companion object {
    private const val TEST_SEARCH_TERM = "search_term"
    private const val TEST_WORD = "word"
  }
}