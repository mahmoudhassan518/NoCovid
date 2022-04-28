package com.ksa.unticovid.modules.family.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.family.domain.param.GetFamilyMembersUseCase
import com.ksa.unticovid.modules.family.presentation.model.FamilyEffects
import com.ksa.unticovid.modules.family.presentation.model.FamilyMembersUIModel
import com.ksa.unticovid.modules.family.presentation.model.mapper.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FamilyMembersViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val getFamilyMembersUseCase: GetFamilyMembersUseCase
) :
    BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(FamilyMembersUIModel())
    val membersUiState: StateFlow<FamilyMembersUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<FamilyEffects>(replay = 0)
    val uiEffects: SharedFlow<FamilyEffects> = _uiEffects

    fun getFamilyMembers() = launchBlock(onStart = { donOnStart() }, onError = { showError() }) {
        getFamilyMembersUseCase(Unit).collectLatest { reports ->
            _uiState.value =
                _uiState.value.copy(
                    isLoading = false,
                    emptyMessage = buildEmptyMessage(reports.isNullOrEmpty()),
                    family = reports.map { it.toUIModel() })
        }
    }

    private fun buildEmptyMessage(isEmpty: Boolean) =
        if (isEmpty) R.string.emptyReportMessage else null

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true, errorMessage = null, emptyMessage = null)
    }

    private fun showError() {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = if (_uiState.value.family.isNullOrEmpty()) R.string.reportErrorMessage else null
            )

        if (!_uiState.value.family.isNullOrEmpty())
            viewModelScope.launch {
                _uiEffects.emit(FamilyEffects.ShowError(UIError.getUnexpectedError().msg))
            }
    }
}
