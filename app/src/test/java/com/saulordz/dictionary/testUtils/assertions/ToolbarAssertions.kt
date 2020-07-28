package com.saulordz.dictionary.testUtils.assertions

import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import assertk.Assert
import assertk.assertions.isEqualTo
import com.saulordz.dictionary.R
import org.robolectric.Shadows

internal fun Assert<Toolbar>.isBackToolbar() = given { actual ->
  val homeButtonDrawable = Shadows.shadowOf(actual.homeButton?.drawable)
  assertThat(homeButtonDrawable.createdFromResId).isEqualTo(R.drawable.ic_back_arrow)
}

private val Toolbar.homeButton: ImageButton?
  get() {
    val children = (0 until childCount).map { getChildAt(it) }
    return children.filterIsInstance<ImageButton>()
      .firstOrNull { it.drawable != null }
  }