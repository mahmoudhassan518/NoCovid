package com.ksa.unticovid.modules.user_management.core.presentation.viewmodel

import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.mahmoud.myframework.modules.splash.domain.IntroScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class UserManagementViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher
) :
    BaseViewModel(mainDispatcher) {

    private val _event: MutableSharedFlow<IntroScreen> = MutableSharedFlow()
    val event = _event.asSharedFlow()
}
