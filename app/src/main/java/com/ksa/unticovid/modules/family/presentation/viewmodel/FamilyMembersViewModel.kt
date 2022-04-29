package com.ksa.unticovid.modules.family.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.family.domain.param.GetFamilyMembersUseCase
import com.ksa.unticovid.modules.family.presentation.model.FamilyMemberDataUIModel
import com.ksa.unticovid.modules.family.presentation.model.FamilyMembersEffects
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
    val uiState: StateFlow<FamilyMembersUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<FamilyMembersEffects>(replay = 0)
    val uiEffects: SharedFlow<FamilyMembersEffects> = _uiEffects

    fun addFamilyMemberToList(member: FamilyMemberDataUIModel) {
        uiState.value.family.add(member)
        _uiState.value =
            _uiState.value.copy(
                emptyMessage = null
            )
    }


    fun getFamilyMembers(id: String) =
        launchBlock(onStart = { donOnStart() }, onError = { showError() }) {
            getFamilyMembersUseCase(id).collectLatest { data ->
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        emptyMessage = buildEmptyMessage(data.isNullOrEmpty()),
                        family = data.map { it.toUIModel() }.toMutableList()
                    )
            }
        }

    private fun buildEmptyMessage(isEmpty: Boolean) =
        if (isEmpty) R.string.emptyFamilyMessage else null

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true, errorMessage = null, emptyMessage = null)
    }

    private fun showError() {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = if (_uiState.value.family.isNullOrEmpty()) R.string.familyErrorMessage else null
            )

        if (!_uiState.value.family.isNullOrEmpty())
            viewModelScope.launch {
                _uiEffects.emit(FamilyMembersEffects.ShowError(UIError.getUnexpectedError().msg))
            }
    }
}
