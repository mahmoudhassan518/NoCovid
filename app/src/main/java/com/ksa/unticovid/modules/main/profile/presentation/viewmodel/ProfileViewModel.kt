package com.ksa.unticovid.modules.main.profile.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.exception.EmptyFieldsException
import com.ksa.unticovid.core.exception.InvalidEmailException
import com.ksa.unticovid.core.exception.NoChangeException
import com.ksa.unticovid.core.exception.RemoteException
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.main.profile.presentation.model.ProfileEffects
import com.ksa.unticovid.modules.main.profile.presentation.model.ProfileUIModel
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user_management.user.domain.entity.param.UserParam
import com.ksa.unticovid.modules.user_management.user.domain.interactor.GetLocalUserUseCase
import com.ksa.unticovid.modules.user_management.user.domain.interactor.GetRemoteUserDataUseCase
import com.ksa.unticovid.modules.user_management.user.domain.interactor.SaveRemoteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val saveRemoteUserUseCase: SaveRemoteUserUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val getRemoteUserDataUseCase: GetRemoteUserDataUseCase,
) : BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(ProfileUIModel())
    val uiState: StateFlow<ProfileUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<ProfileEffects>(replay = 0)
    val uiEffects: SharedFlow<ProfileEffects> = _uiEffects

    private fun updateEffect(effect: ProfileEffects) = viewModelScope.launch { _uiEffects.emit(effect) }

    init {
        getLocalUserData()
        getRemoteUserData()
    }

    private fun getLocalUserData() =
        launchBlock {
            getLocalUserUseCase(Unit).collectLatest {
                onGetUserSuccessfully(it)
            }
        }

    fun getRemoteUserData() =
        launchBlock(
            onStart = { donOnStart() },
            onError = { onLoadProfileError() }
        ) {
            getRemoteUserDataUseCase(Unit).collectLatest {
                onGetUserSuccessfully(it)
            }
        }

    fun saveProfileData(userParam: UserParam) =
        launchBlock(
            onStart = { donOnStart() },
            onError = { it.onSaveProfileError() }
        ) {
            saveRemoteUserUseCase(userParam).collectLatest {
                viewModelScope.launch {
                    _uiEffects.emit(ProfileEffects.ShowProfileSuccessfulAlert(it.message))
                }
                onGetUserSuccessfully(it.response)
            }
        }

    private fun onGetUserSuccessfully(user: UserEntity) {
        _uiState.value = _uiState.value.copy(user = user, isLoading = false)
        updateEffect(ProfileEffects.DisplayUserData(user))
    }

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true)
    }

    private fun Throwable.onSaveProfileError() =
        when (this) {
            is RemoteException -> showErrorRemote()
            is InvalidEmailException -> showErrorResource(UIError.getInvalidEmailAddressError())
            is EmptyFieldsException -> showErrorResource(UIError.hasBlankFieldsError())
            is NoChangeException ->  updateEffect(ProfileEffects.ShowToast(R.string.noChangesFoundMsg))
            else -> showErrorResource(UIError.getUnexpectedError())
        }.also {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }

    private fun onLoadProfileError() =
        showErrorResource(UIError.failedToGetUserDataError()).also {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }

    private fun showErrorResource(error: UIError) =
        updateEffect(ProfileEffects.ShowResourceError(error))

    private fun RemoteException.showErrorRemote() =
        updateEffect(ProfileEffects.ShowRemoteError(message!!))
}
