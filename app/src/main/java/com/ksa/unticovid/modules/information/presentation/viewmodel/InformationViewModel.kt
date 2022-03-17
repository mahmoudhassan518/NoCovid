package com.ksa.unticovid.modules.information.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.common.di.MainDispatcher
import com.ksa.unticovid.modules.information.data.repository.InformationRepository
import com.ksa.unticovid.modules.information.presentation.model.InformationEffects
import com.ksa.unticovid.modules.information.presentation.model.InformationUIModel
import com.ksa.unticovid.modules.information.presentation.model.mapper.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val repository: InformationRepository
) :
    BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(InformationUIModel())
    val uiState: StateFlow<InformationUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<InformationEffects>(replay = 0)
    val uiEffects: SharedFlow<InformationEffects> = _uiEffects

    fun getCovidInformation() =
        launchBlock(onStart = { donOnStart() }, onError = { showError() }) {
            repository.getInformation().collectLatest { factions ->
                _uiState.value =
                    _uiState.value.copy(isLoading = false, information = factions.toUIModel())
            }
        }

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true, errorMessage = null)
    }

    private fun showError() {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = if (_uiState.value.information == null) R.string.msgSomethingWentWrong else null
            )

        if (_uiState.value.information != null)
            viewModelScope.launch {
                _uiEffects.emit(InformationEffects.ShowInformationError(R.string.msgSomethingWentWrong))
            }
    }
}
