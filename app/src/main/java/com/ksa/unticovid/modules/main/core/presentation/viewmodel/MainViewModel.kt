package com.ksa.unticovid.modules.main.core.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.main.core.presentation.model.MainEffects
import com.ksa.unticovid.modules.main.core.presentation.model.MainUIModel
import com.ksa.unticovid.modules.main.report.presentation.model.ReportEffects
import com.ksa.unticovid.modules.user_management.user.domain.interactor.DeleteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val deleteUserUseCase: DeleteUserUseCase
) :
    BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(MainUIModel())
    val uiState: StateFlow<MainUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<MainEffects>(replay = 0)
    val uiEffects: SharedFlow<MainEffects> = _uiEffects

    fun showAppActionBar(showActionBar: Boolean = true) {
        _uiState.value = _uiState.value.copy(showAppActionBar = showActionBar)
    }

    fun signOut() {
       launchBlock {
           deleteUserUseCase(Unit).collectLatest {
               viewModelScope.launch {
                   _uiEffects.emit(MainEffects.SignOut)
               }
           }
       }
    }

}
