package com.ksa.unticovid.modules.splash.presentaion.viewmodel

import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.splash.domain.interactor.GetIntroScreenUseCase
import com.mahmoud.myframework.modules.splash.domain.IntroScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val getIntroScreenUseCase: GetIntroScreenUseCase
) :
    BaseViewModel(mainDispatcher) {

    private val _event: MutableSharedFlow<IntroScreen> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    fun start() {
        launchBlock(onError = {}) {
            delay(SPLASH_TIME)
            getIntroScreenUseCase(Unit).collectLatest {
                _event.emit(it)
            }
        }
    }

    companion object {
        private const val SPLASH_TIME: Long = 3000
    }
}
