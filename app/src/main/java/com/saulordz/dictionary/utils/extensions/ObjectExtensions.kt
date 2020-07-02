package com.saulordz.dictionary.utils.extensions

internal fun <A> A?.orDefault(other: A): A {
  return this ?: other
}