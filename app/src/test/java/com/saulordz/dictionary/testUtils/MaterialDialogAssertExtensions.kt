package com.saulordz.dictionary.testUtils

import android.widget.TextView
import assertk.Assert
import assertk.assertions.isEqualTo
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton.NEGATIVE
import com.afollestad.materialdialogs.WhichButton.POSITIVE
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.list.getListAdapter
import com.saulordz.dictionary.R

internal fun Assert<MaterialDialog>.hasTitle(expectedText: String) = given { actual ->
  assertThat(actual.findViewById<TextView>(R.id.md_text_title).text).isEqualTo(expectedText)
}

internal fun Assert<MaterialDialog>.hasPositiveText(expectedText: String) = given { actual ->
  assertThat(actual.getActionButton(POSITIVE).text).isEqualTo(expectedText)
}

internal fun Assert<MaterialDialog>.hasNegativeText(expectedText: String) = given { actual ->
  assertThat(actual.getActionButton(NEGATIVE).text).isEqualTo(expectedText)
}

internal fun Assert<MaterialDialog>.hasItemCount(itemCount: Int) = given { actual ->
  assertThat(actual.getListAdapter()?.itemCount).isEqualTo(itemCount)
}


