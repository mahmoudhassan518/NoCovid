package com.ksa.unticovid.modules.upload.presentation.viewmodel

import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.upload.presentation.model.UploadUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(UploadUIModel())
    val uiState: StateFlow<UploadUIModel> = _uiState

    fun uploadImage() = launchBlock(onStart = { donOnStart() },
        onError = { showError(R.string.analyticsErrorMessage) }) {

    }

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true, errorMessage = null)
    }

    fun showError(message: Int?) {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = message
            )
    }

}