package com.saulordz.dictionary.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApplicationSchedulerProvider @Inject constructor() : SchedulerProvider {
  override val io: Scheduler = Schedulers.io()
  override val ui: Scheduler = AndroidSchedulers.mainThread()
}