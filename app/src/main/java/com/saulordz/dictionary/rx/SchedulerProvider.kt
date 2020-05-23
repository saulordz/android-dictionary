package com.saulordz.dictionary.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
  val io: Scheduler
  val ui: Scheduler
}