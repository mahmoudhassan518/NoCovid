package com.ksa.unticovid.modules.user_management.signin.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.exception.EmptyFieldsException
import com.ksa.unticovid.core.exception.InvalidNationalIdException
import com.ksa.unticovid.core.exception.RemoteException
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.user_management.signin.domain.entity.param.SignInParam
import com.ksa.unticovid.modules.user_management.signin.domain.interactor.SignInUseCase
import com.ksa.unticovid.modules.user_management.signin.presentation.model.SignInEffects
import com.ksa.unticovid.modules.user_management.signin.presentation.model.SignInUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val signInUseCase: SignInUseCase
) : BaseViewModel(mainDispatcher) {

    private val _uiEffects = MutableSharedFlow<SignInEffects>(replay = 0)
    val uiEffects: SharedFlow<SignInEffects> = _uiEffects

    private val _uiState = MutableStateFlow(SignInUIModel())
    val uiState: StateFlow<SignInUIModel> = _uiState

    fun signIn(param: SignInParam) =
        launchBlock(
            onStart = { onRequestSignInLoading() },
            onError = { it.onRequestSignInError() }
        ) {
            signInUseCase.invoke(param).collectLatest { user ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    user = user
                )
                onRequestSignInSuccess()
            }
        }

    private fun onRequestSignInLoading() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
        )
    }

    private fun onRequestSignInSuccess() =
        viewModelScope.launch {
            _uiEffects.emit(SignInEffects.NavigateToMainScreen)
        }

    private fun Throwable.onRequestSignInError() =
        when (this) {
            is InvalidNationalIdException -> showErrorResource(UIError.getInvalidNationalIdError())
            is EmptyFieldsException -> showErrorResource(UIError.hasBlankFieldsError())
            is RemoteException -> showErrorRemote()
            else -> showErrorResource(UIError.getUnexpectedError())
        }.also {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }


    private fun showErrorResource(error: UIError) =
        viewModelScope.launch {
            _uiEffects.emit(SignInEffects.ShowResourceError(error))
        }

    private fun Throwable.showErrorRemote() =
        viewModelScope.launch {
            _uiEffects.emit(SignInEffects.ShowRemoteError(message!!))
        }

}
