package com.saulordz.dictionary.utils.extensions

internal fun String.stripSymbols() = this.filter { it.isLetterOrDigit() }