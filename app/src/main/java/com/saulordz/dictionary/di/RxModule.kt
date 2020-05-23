package com.saulordz.dictionary.di

import com.saulordz.dictionary.rx.ApplicationSchedulerProvider
import com.saulordz.dictionary.rx.SchedulerProvider
import toothpick.config.Module

class RxModule : Module() {
  init {
    bind(SchedulerProvider::class.java).to(ApplicationSchedulerProvider::class.java)
  }
}