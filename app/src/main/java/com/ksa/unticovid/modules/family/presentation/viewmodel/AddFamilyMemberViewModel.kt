package com.ksa.unticovid.modules.family.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.exception.EmptyFieldsException
import com.ksa.unticovid.core.exception.InvalidNationalIdException
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam
import com.ksa.unticovid.modules.family.domain.param.AddFamilyMemberUseCase
import com.ksa.unticovid.modules.family.presentation.model.AddFamilyMemberEffects
import com.ksa.unticovid.modules.family.presentation.model.AddFamilyMemberUIModel
import com.ksa.unticovid.modules.family.presentation.model.FamilyMemberDataUIModel
import com.ksa.unticovid.modules.family.presentation.model.mapper.toUIModel
import com.ksa.unticovid.modules.user_management.core.presentation.model.mapper.genderUISettings
import com.ksa.unticovid.modules.user_management.user.domain.entity.GenderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFamilyMemberViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val addFamilyMemberUseCase: AddFamilyMemberUseCase
) : BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(AddFamilyMemberUIModel())
    val uiState: StateFlow<AddFamilyMemberUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<AddFamilyMemberEffects>(replay = 0)
    val uiEffects: SharedFlow<AddFamilyMemberEffects> = _uiEffects

    fun updateCurrentGender(it: GenderType) {
        _uiState.value = _uiState.value.copy(
            currentGender = it, genderSettings = it.genderUISettings()
        )
    }

    fun addFamilyMember(param: AddFamilyMemberParam) =
        launchBlock(
            onStart = { onRequestAddFamilyMemberLoading() },
            onError = { it.onAddFamilyMemberError() }
        ) {
            addFamilyMemberUseCase.invoke(param.copy(gender = uiState.value.currentGender.value))
                .collectLatest {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        currentFamilyMemberData = it.toUIModel()
                    )
                    onAddFamilyMemberSuccess(it.toUIModel())
                }
        }

    private fun onRequestAddFamilyMemberLoading() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
        )
    }

    private fun onAddFamilyMemberSuccess(it: FamilyMemberDataUIModel) =
        viewModelScope.launch {
            _uiEffects.emit(AddFamilyMemberEffects.ShowSuccessSubmitFamilyMembersMember(it))
        }

    private fun Throwable.onAddFamilyMemberError() =
        when (this) {
            is InvalidNationalIdException -> showErrorResource(UIError.getInvalidNationalIdError())
            is EmptyFieldsException -> showErrorResource(UIError.hasBlankFieldsError())
            else -> showErrorResource(UIError.getUnexpectedError())
        }.also {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }

    private fun showErrorResource(error: UIError) =
        viewModelScope.launch {
            _uiEffects.emit(AddFamilyMemberEffects.ShowError(error.msg))
        }
}

