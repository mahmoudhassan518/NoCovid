package com.ksa.unticovid.core.utils

typealias Action = () -> Unit
typealias Producer<T> = () -> T
typealias Reducer<T> = T.() -> T
typealias Consumer<T> = (T) -> Unit
