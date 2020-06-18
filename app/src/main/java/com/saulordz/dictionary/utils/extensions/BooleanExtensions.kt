package com.saulordz.dictionary.utils.extensions

internal fun Boolean?.orFalse() = this ?: false

internal fun Boolean?.orTrue() = this ?: true