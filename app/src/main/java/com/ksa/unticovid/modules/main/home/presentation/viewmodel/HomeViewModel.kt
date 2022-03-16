package com.ksa.unticovid.modules.main.home.presentation.viewmodel

import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.common.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(@MainDispatcher val mainDispatcher: CoroutineDispatcher) :
    BaseViewModel(mainDispatcher)
