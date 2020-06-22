package com.saulordz.dictionary.utils.helpers

import android.content.Context
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.customListAdapter
import com.saulordz.dictionary.R
import com.saulordz.dictionary.ui.home.dialog.LanguageDialogAdapter
import com.saulordz.dictionary.utils.extensions.removeItemAnimator


object MaterialDialogHelper {

  internal fun showLanguagePickerDialog(context: Context, onApplyClickedListener: DialogCallback, languageDialogAdapter: LanguageDialogAdapter) =
    createListDialog(
      context,
      R.string.message_select_language,
      R.string.message_apply,
      R.string.message_cancel,
      onApplyClickedListener,
      languageDialogAdapter
    ).removeItemAnimator()
      .show()

  private fun <VH : RecyclerView.ViewHolder> createListDialog(
    context: Context,
    @StringRes titleStringRes: Int,
    @StringRes positiveButtonStringRes: Int,
    @StringRes negativeButtonStringRes: Int,
    onApplyClickedListener: DialogCallback,
    customAdapter: RecyclerView.Adapter<VH>
  ) =
    MaterialDialog(context)
      .title(titleStringRes)
      .positiveButton(positiveButtonStringRes, click = onApplyClickedListener)
      .negativeButton(negativeButtonStringRes)
      .customListAdapter(customAdapter)
}
