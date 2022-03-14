package com.ksa.unticovid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.core.utils.Action
import com.ksa.unticovid.core.utils.Consumer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val mainDispatcher: CoroutineDispatcher) : ViewModel() {

    fun launchBlock(onStart: Action? = null, onError: Consumer<Throwable>? = null, block: suspend CoroutineScope.() -> Unit) {
        onStart?.invoke()
        viewModelScope.launch(mainDispatcher + coroutineExceptionHandler(onError)) {
            block.invoke(this)
        }
    }

    private fun coroutineExceptionHandler(onError: Consumer<Throwable>?) =
        CoroutineExceptionHandler { _, throwable ->
            onError?.invoke(throwable)
        }
}
