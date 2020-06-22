package com.saulordz.dictionary.testUtils

import com.saulordz.dictionary.rx.SchedulerComposer
import com.saulordz.dictionary.rx.SchedulerProvider
import io.reactivex.schedulers.Schedulers

object RxTestUtils {
  internal val schedulerComposer = SchedulerComposer(object : SchedulerProvider {
    override val io = Schedulers.trampoline()
    override val ui = Schedulers.trampoline()
  })
}