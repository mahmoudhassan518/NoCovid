package com.ksa.unticovid.modules.faction.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.common.di.MainDispatcher
import com.ksa.unticovid.modules.faction.data.repository.FactionRepository
import com.ksa.unticovid.modules.faction.presentation.model.FactionsEffects
import com.ksa.unticovid.modules.faction.presentation.model.FactionsUIModel
import com.ksa.unticovid.modules.faction.presentation.model.mapper.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactionsViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val repository: FactionRepository
) :
    BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(FactionsUIModel())
    val uiState: StateFlow<FactionsUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<FactionsEffects>(replay = 0)
    val uiEffects: SharedFlow<FactionsEffects> = _uiEffects

    fun getUserFactions() =
        launchBlock(onStart = { donOnStart() }, onError = { showError() }) {
            repository.getFactions().collectLatest { factions ->
                _uiState.value =
                    _uiState.value.copy(isLoading = false, factions = factions.toUIModel())
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
                errorMessage = if (_uiState.value.factions == null) R.string.msgSomethingWentWrong else null
            )

        if (_uiState.value.factions != null)
            viewModelScope.launch {
                _uiEffects.emit(FactionsEffects.ShowFactionsError(R.string.msgSomethingWentWrong))
            }
    }
}
