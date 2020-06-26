package com.saulordz.dictionary.utils.helpers

import androidx.recyclerview.widget.RecyclerView
import assertk.assertThat
import assertk.assertions.isTrue
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.testUtils.assertions.*
import com.saulordz.dictionary.testUtils.assertions.hasAdapter
import com.saulordz.dictionary.testUtils.assertions.hasNegativeText
import com.saulordz.dictionary.testUtils.assertions.hasPositiveText
import com.saulordz.dictionary.testUtils.assertions.hasTitle
import com.saulordz.dictionary.ui.home.dialog.LanguageDialogAdapter
import com.saulordz.dictionary.utils.extensions.performPositiveClick
import org.junit.Test
import org.robolectric.shadows.ShadowAlertDialog

class MaterialDialogHelperTest : BaseActivityTest() {

  @Suppress("UNCHECKED_CAST")
  @Test
  fun testShowLanguagePickerDialog() {
    var wasCalled = false
    val onApplyClickListener: DialogCallback = {
      wasCalled = true
    }
    val adapter = LanguageDialogAdapter {}

    MaterialDialogHelper.showLanguagePickerDialog(application, onApplyClickListener, adapter)

    val dialog = ShadowAlertDialog.getLatestDialog() as MaterialDialog
    assertThat(dialog.isShowing).isTrue()
    assertThat(dialog).hasTitle(application.getString(R.string.message_select_language))
    assertThat(dialog).hasPositiveText(application.getString(R.string.message_apply))
    assertThat(dialog).hasNegativeText(application.getString(R.string.message_cancel))
    assertThat(dialog).hasAdapter(adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
    assertThat(dialog).hasNoItemAnimator()
    dialog.performPositiveClick()
    assertThat(wasCalled).isTrue()
  }
}