package com.saulordz.dictionary.ui.home

import com.saulordz.dictionary.base.BasePresenter
import com.saulordz.dictionary.data.model.Word
import com.saulordz.dictionary.data.repository.GoogleDictionaryRepository
import com.saulordz.dictionary.rx.SchedulerComposer
import io.reactivex.Observable
import javax.inject.Inject

class HomePresenter @Inject constructor(
  schedulerComposer: SchedulerComposer,
  private val googleDictionaryRepository: GoogleDictionaryRepository
) : BasePresenter<HomeContract.View>(schedulerComposer), HomeContract.Presenter {

  override fun registerSearchButtonObservable(observable: Observable<Unit>) = observable.onObservableSuccess { view ->
    search(view.searchTerm)
  }

  private fun search(searchTerm: String) = addDisposable {
    googleDictionaryRepository.singleSearchWord(searchTerm)
      .compose(schedulerComposer.singleComposer())
      .subscribe(::handleSearchSuccess) { onError(it) }
  }

  private fun handleSearchSuccess(result: Word) = ifViewAttached { view ->
    view.displaySearchResult(result.definitions?.values?.flatten())
  }

  override fun onError(throwable: Throwable?, message: String) = ifViewAttached { view ->
    super.onError(throwable, message)
    view.displayError()
  }
}