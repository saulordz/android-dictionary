package com.saulordz.dictionary.utils.widgets

import assertk.assertThat
import assertk.assertions.isTrue
import com.afollestad.materialdialogs.MaterialDialog
import com.saulordz.dictionary.R
import com.saulordz.dictionary.base.BaseActivityTest
import com.saulordz.dictionary.testUtils.hasItemCount
import com.saulordz.dictionary.testUtils.hasNegativeText
import com.saulordz.dictionary.testUtils.hasPositiveText
import com.saulordz.dictionary.testUtils.hasTitle
import org.junit.Test
import org.robolectric.shadows.ShadowAlertDialog

class MaterialDialogHelperTest : BaseActivityTest() {

  @Test
  fun testShowLanguagePickerDialog() {
    MaterialDialogHelper.showLanguagePickerDialog(application) { _, _, _ -> }

    val dialog = ShadowAlertDialog.getLatestDialog() as MaterialDialog

    assertThat(dialog.isShowing).isTrue()
    assertThat(dialog).hasTitle(application.getString(R.string.message_select_language))
    assertThat(dialog).hasPositiveText(application.getString(R.string.message_apply))
    assertThat(dialog).hasNegativeText(application.getString(R.string.message_cancel))
    assertThat(dialog).hasItemCount(2)
  }
}