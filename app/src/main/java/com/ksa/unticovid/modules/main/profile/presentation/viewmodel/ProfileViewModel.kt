package com.ksa.unticovid.modules.main.profile.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.exception.EmptyFieldsException
import com.ksa.unticovid.core.exception.InvalidNationalIdException
import com.ksa.unticovid.modules.common.di.MainDispatcher
import com.ksa.unticovid.modules.main.profile.presentation.model.ProfileEffects
import com.ksa.unticovid.modules.main.profile.presentation.model.ProfileUIModel
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.user.domain.entity.param.UserParam
import com.ksa.unticovid.modules.user.domain.interactor.GetLocalUserUseCase
import com.ksa.unticovid.modules.user.domain.interactor.GetRemoteUserDataUseCase
import com.ksa.unticovid.modules.user.domain.interactor.SaveRemoteUserUseCase
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
) :
    BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(ProfileUIModel())
    val uiState: StateFlow<ProfileUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<ProfileEffects>(replay = 0)
    val uiEffects: SharedFlow<ProfileEffects> = _uiEffects

    init {
        getLocalUserData()
        getRemoteUserData()
    }

    private fun getLocalUserData() =
        launchBlock {
            getLocalUserUseCase(Unit).collectLatest {
                _uiState.value = _uiState.value.copy(user = it)
            }
        }

    fun getRemoteUserData() =
        launchBlock(
            onStart = { donOnStart() },
            onError = { showError(UIError.failedToGetUserData()) }
        ) {
            getRemoteUserDataUseCase(Unit).collectLatest {
                _uiState.value = _uiState.value.copy(user = it, isLoading = false)
            }
        }

    fun saveProfileData(userParam: UserParam) =
        launchBlock(
            onStart = { donOnStart() },
            onError = { onSaveProfileError(it) }
        ) {
            saveRemoteUserUseCase(userParam).collectLatest {
                viewModelScope.launch {
                    _uiEffects.emit(ProfileEffects.ShowProfileSuccessfulAlert(R.string.profileUpdatedSuccessfullyMessage))
                }
                _uiState.value = _uiState.value.copy(user = it, isLoading = false)
            }
        }

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true)
    }

    private fun onSaveProfileError(throwable: Throwable) {
        when (throwable) {
            is InvalidNationalIdException -> showError(UIError.invalidNationalId())
            is EmptyFieldsException -> showError(UIError.hasBlankFields())
            else -> showError(UIError.getUnexpectedException())
        }
    }

    private fun showError(error: UIError) {
        _uiState.value = _uiState.value.copy(isLoading = false)
        viewModelScope.launch {
            _uiEffects.emit(ProfileEffects.ShowProfileError(error))
        }
    }
}
