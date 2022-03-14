package com.ksa.unticovid.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

abstract class BaseRepository(
    private val ioDispatcher: CoroutineDispatcher
) {

    fun <T> requestHandler(fetch: suspend () -> T) = flow {
        try {
            emit(fetch.invoke())
        } catch (throwable: Throwable) {
            // handle throwable type
            Timber.e("error happen $throwable")
            throw throwable
        }
    }.flowOn(ioDispatcher)
}
