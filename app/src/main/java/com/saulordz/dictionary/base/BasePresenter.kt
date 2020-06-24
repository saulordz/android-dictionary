package com.saulordz.dictionary.base

import android.content.Context
import android.view.inputmethod.EditorInfo
import androidx.annotation.VisibleForTesting
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.jakewharton.rxbinding3.widget.TextViewEditorActionEvent
import com.saulordz.dictionary.data.model.Language
import com.saulordz.dictionary.data.repository.SharedPreferencesRepository
import com.saulordz.dictionary.rx.SchedulerComposer
import com.saulordz.dictionary.utils.helpers.LocaleListHelper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class BasePresenter<V : BaseContract.View>(
  open val schedulerComposer: SchedulerComposer,
  open val preferencesRepository: SharedPreferencesRepository
) : MvpBasePresenter<V>(), BaseContract.Presenter<V> {

  @VisibleForTesting
  internal var compositeDisposable: CompositeDisposable? = null

  override fun attachView(view: V) {
    super.attachView(view)
    compositeDisposable = CompositeDisposable()
  }

  override fun detachView() {
    compositeDisposable?.dispose()
    compositeDisposable = null
    super.detachView()
  }

  override fun createCustomContext(context: Context?): Context? {
    val userLanguage = preferencesRepository.getUserPreferredLanguage()
    return LocaleListHelper.createConfigurationContext(context, userLanguage.languageTag)
  }

  internal fun updateUserLanguage(newLanguage: Language) = ifViewAttached { view ->
    preferencesRepository.saveUserPreferredLanguage(newLanguage)
    view.recreateView()
  }

  internal fun addDisposable(actionToDispose: () -> Disposable) {
    compositeDisposable?.add(actionToDispose())
  }

  internal open fun onError(throwable: Throwable? = null, message: String = DEFAULT_ERROR_MESSAGE) {
    if (throwable != null) {
      Timber.e(throwable)
    } else {
      Timber.e(message)
    }
  }

  internal fun Observable<Unit>.onObservableAction(onSuccess: (V) -> Unit) = addDisposable {
    share()
      .subscribe({ ifViewAttached { onSuccess(it) } }) { onError(it) }
  }

  internal fun Observable<TextViewEditorActionEvent>.onObservableSearchAction(onSuccess: (V) -> Unit) = addDisposable {
    share()
      .filter { it.actionId == EditorInfo.IME_ACTION_SEARCH }
      .subscribe({ ifViewAttached { onSuccess(it) } }) { onError(it) }
  }

  private companion object {
    private const val DEFAULT_ERROR_MESSAGE = "Unknown error"
  }
}