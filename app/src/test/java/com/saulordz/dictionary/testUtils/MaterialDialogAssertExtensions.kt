package com.saulordz.dictionary.testUtils

import android.widget.TextView
import assertk.Assert
import assertk.assertions.isEqualTo
import com.afollestad.materialdialogs.MaterialDialog
import com.saulordz.dictionary.R

internal fun Assert<MaterialDialog>.hasTitle(expectedText: String) = given { actual ->
  assertThat(actual.findViewById<TextView>(R.id.md_text_title).text).isEqualTo(expectedText)
}

internal fun Assert<MaterialDialog>.hasPositiveText(expectedText: String) = given { actual ->
  assertThat(actual.findViewById<TextView>(R.id.md_button_positive).text).isEqualTo(expectedText)
}

internal fun Assert<MaterialDialog>.hasNegativeText(expectedText: String) = given { actual ->
  assertThat(actual.findViewById<TextView>(R.id.md_button_negative).text).isEqualTo(expectedText)
}

internal fun Assert<MaterialDialog>.hasItemCount(itemCount: Int) = given { actual ->
  assertThat(actual.view.contentLayout.recyclerView?.adapter?.itemCount).isEqualTo(itemCount)
}


