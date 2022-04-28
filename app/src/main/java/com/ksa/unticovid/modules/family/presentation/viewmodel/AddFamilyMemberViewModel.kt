package com.ksa.unticovid.modules.family.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.exception.EmptyFieldsException
import com.ksa.unticovid.core.exception.InvalidNationalIdException
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam
import com.ksa.unticovid.modules.family.domain.param.AddFamilyMemberUseCase
import com.ksa.unticovid.modules.family.presentation.model.AddFamilyMemberUIModel
import com.ksa.unticovid.modules.family.presentation.model.FamilyEffects
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

    private val _uiEffects = MutableSharedFlow<FamilyEffects>(replay = 0)
    val uiEffects: SharedFlow<FamilyEffects> = _uiEffects

    fun addFamilyMember(param: AddFamilyMemberParam) =
        launchBlock(
            onStart = { onRequestAddFamilyMemberLoading() },
            onError = { it.onAddFamilyMemberError() }
        ) {
            addFamilyMemberUseCase.invoke(param.copy(gender = uiState.value.currentGender.value))
                .collectLatest {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                    )
                    onAddFamilyMemberSuccess()
                }
        }

    private fun onRequestAddFamilyMemberLoading() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
        )
    }

    private fun onAddFamilyMemberSuccess() =
        viewModelScope.launch {
            _uiEffects.emit(FamilyEffects.ShowSuccessSubmitFamilyMember(R.string.msgMemberFamilyAddedSuccessfully))
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
            _uiEffects.emit(FamilyEffects.ShowError(error.msg))
        }
}

