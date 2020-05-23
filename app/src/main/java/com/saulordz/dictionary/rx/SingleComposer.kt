package com.saulordz.dictionary.rx

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleTransformer

internal class SingleComposer(
  private val subscribeScheduler: Scheduler,
  private val observeScheduler: Scheduler = subscribeScheduler
) : SingleTransformer<Any, Any> {

  override fun apply(upstream: Single<Any>) =
    upstream.subscribeOn(subscribeScheduler).observeOn(observeScheduler)

}