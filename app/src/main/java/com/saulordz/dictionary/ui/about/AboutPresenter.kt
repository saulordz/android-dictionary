package com.saulordz.dictionary.ui.about

import com.saulordz.dictionary.BuildConfig
import com.saulordz.dictionary.base.BasePresenter
import com.saulordz.dictionary.data.repository.SharedPreferencesRepository
import com.saulordz.dictionary.rx.SchedulerComposer
import javax.inject.Inject

class AboutPresenter @Inject constructor(
  schedulerComposer: SchedulerComposer,
  preferencesRepository: SharedPreferencesRepository
) : BasePresenter<AboutContract.View>(schedulerComposer, preferencesRepository), AboutContract.Presenter {

  override fun initialize() = ifViewAttached { view ->
    view.versionNumber = BuildConfig.VERSION_NAME
  }

}