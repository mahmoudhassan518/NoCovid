package com.ksa.unticovid.modules.user_management.signup.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.exception.*
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.user_management.core.presentation.model.mapper.genderUISettings
import com.ksa.unticovid.modules.user_management.signup.domain.entity.param.SignUpParam
import com.ksa.unticovid.modules.user_management.signup.domain.interactor.SignUpUseCase
import com.ksa.unticovid.modules.user_management.signup.presentation.model.SignUpEffects
import com.ksa.unticovid.modules.user_management.signup.presentation.model.SignUpUIModel
import com.ksa.unticovid.modules.user_management.user.domain.entity.GenderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel(mainDispatcher) {

    private val _uiEffects = MutableSharedFlow<SignUpEffects>(replay = 0)
    val uiEffects: SharedFlow<SignUpEffects> = _uiEffects

    private val _uiState = MutableStateFlow(SignUpUIModel())
    val uiState: StateFlow<SignUpUIModel> = _uiState

    fun updateCurrentGender(type: GenderType) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            currentGender = type,
            genderSettings = type.genderUISettings()
        )
    }

    fun signUp(param: SignUpParam) =
        launchBlock(
            onStart = { onRequestLoginLoading() },
            onError = { it.onRequestSignUpError() }
        ) {
            signUpUseCase.invoke(param.copy(gender = uiState.value.currentGender.value))
                .collectLatest { user ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        user = user
                    )
                    onRequestSignUpSuccess()
                }
        }

    private fun onRequestLoginLoading() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
        )
    }

    private fun onRequestSignUpSuccess() =
        viewModelScope.launch {
            _uiEffects.emit(SignUpEffects.NavigateToMainScreen)
        }

    private fun Throwable.onRequestSignUpError() =
        when (this) {
            is InvalidNationalIdException -> showErrorResource(UIError.getInvalidNationalIdError())
            is InvalidEmailException -> showErrorResource(UIError.getInvalidEmailAddressError())
            is EmptyFieldsException -> showErrorResource(UIError.hasBlankFieldsError())
            is PasswordMissMatchException -> showErrorResource(UIError.getPasswordMissMatchError())
            is PasswordLengthException -> showErrorResource(UIError.getPasswordLengthError())
            is RemoteException -> showErrorRemote()
            else -> showErrorResource(UIError.getUnexpectedError())
        }.also {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }

    private fun showErrorResource(error: UIError) =
        viewModelScope.launch {
            _uiEffects.emit(SignUpEffects.ShowResourceError(error))
        }

    private fun Throwable.showErrorRemote() =
        viewModelScope.launch {
            _uiEffects.emit(SignUpEffects.ShowRemoteError(message!!))
        }
}
