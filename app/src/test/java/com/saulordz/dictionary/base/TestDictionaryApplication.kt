package com.saulordz.dictionary.base

import com.saulordz.dictionary.DictionaryApplication
import toothpick.config.Module
import toothpick.smoothie.module.SmoothieApplicationModule

class TestDictionaryApplication : DictionaryApplication() {

  override val modules: Array<Module>
    get() = arrayOf(SmoothieApplicationModule(this))
}