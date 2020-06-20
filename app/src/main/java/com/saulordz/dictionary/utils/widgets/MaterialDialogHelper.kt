package com.saulordz.dictionary.utils.widgets

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.SingleChoiceListener
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.saulordz.dictionary.R

object MaterialDialogHelper {

  internal fun showLanguagePickerDialog(context: Context, onItemSelectedListener: SingleChoiceListener) =
    createListDialog(
      context,
      R.string.message_select_language,
      R.string.message_apply,
      R.string.message_cancel,
      R.array.array_languages,
      onItemSelectedListener
    ).show()

  private fun createListDialog(
    context: Context,
    @StringRes titleStringRes: Int,
    @StringRes positiveButtonStringRes: Int,
    @StringRes negativeButtonStringRes: Int,
    @ArrayRes itemsArrayRes: Int,
    onItemSelectedListener: SingleChoiceListener
  ) =
    MaterialDialog(context)
      .title(titleStringRes)
      .positiveButton(positiveButtonStringRes)
      .negativeButton(negativeButtonStringRes)
      .listItemsSingleChoice(itemsArrayRes, initialSelection = 0, selection = onItemSelectedListener)

}
