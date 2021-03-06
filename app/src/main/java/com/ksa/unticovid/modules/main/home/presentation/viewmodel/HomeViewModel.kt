package com.ksa.unticovid.modules.main.home.presentation.viewmodel

import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.core.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(@MainDispatcher private val mainDispatcher: CoroutineDispatcher) :
    BaseViewModel(mainDispatcher)
