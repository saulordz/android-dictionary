package com.saulordz.dictionary.base

import androidx.annotation.VisibleForTesting
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.saulordz.dictionary.rx.SchedulerComposer
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class BasePresenter<V : MvpView>(
  open val schedulerComposer: SchedulerComposer
) : MvpBasePresenter<V>(), MvpPresenter<V> {

  @VisibleForTesting
  internal var compositeDisposable: CompositeDisposable? = null

  override fun attachView(view: V) {
    compositeDisposable = CompositeDisposable()
    super.attachView(view)
  }

  override fun detachView() {
    compositeDisposable?.dispose()
    compositeDisposable = null
    super.detachView()
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

  internal fun Observable<Unit>.onObservableSuccess(onSuccess: (V) -> Unit) = addDisposable {
    share().subscribe({ ifViewAttached { onSuccess(it) } }) { onError(it) }
  }

  private companion object {
    private const val DEFAULT_ERROR_MESSAGE = "Unknown error"
  }
}