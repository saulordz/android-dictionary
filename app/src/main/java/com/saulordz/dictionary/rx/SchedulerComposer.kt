package com.saulordz.dictionary.rx

import io.reactivex.SingleTransformer
import javax.inject.Inject

class SchedulerComposer @Inject constructor(schedulerProvider: SchedulerProvider) {

  private val singleComposer = SingleComposer(schedulerProvider.io, schedulerProvider.ui)

  @Suppress("UNCHECKED_CAST")
  fun <T> singleComposer() = singleComposer as SingleTransformer<T, T>

}