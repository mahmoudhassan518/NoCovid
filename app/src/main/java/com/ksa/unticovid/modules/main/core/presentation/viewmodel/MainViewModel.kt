package com.ksa.unticovid.modules.main.core.presentation.viewmodel

import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.common.di.MainDispatcher
import com.ksa.unticovid.modules.main.core.presentation.model.MainUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@MainDispatcher private val mainDispatcher: CoroutineDispatcher) :
    BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(MainUIModel())
    val uiState: StateFlow<MainUIModel> = _uiState

    fun showAppActionBar(showActionBar: Boolean = true) {
        _uiState.value = _uiState.value.copy(showAppActionBar = showActionBar)
    }

}
