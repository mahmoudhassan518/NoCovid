package com.ksa.unticovid.modules.upload.presentation.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.upload.domain.entity.param.UploadReportImageParam
import com.ksa.unticovid.modules.upload.domain.interactor.UploadReportImageUseCase
import com.ksa.unticovid.modules.upload.presentation.model.UploadEffects
import com.ksa.unticovid.modules.upload.presentation.model.UploadUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val uploadReportImageUseCase: UploadReportImageUseCase
) : BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(UploadUIModel())
    val uiState: StateFlow<UploadUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<UploadEffects>(replay = 0)
    val uiEffects: SharedFlow<UploadEffects> = _uiEffects

    private fun updateEffects(effect: UploadEffects) = viewModelScope.launch {
        _uiEffects.emit(effect)
    }


    fun uploadImage(id: String, bitmap: Bitmap, imageUri: Uri) =
        launchBlock(onStart = { donOnStart() },
            onError = { onUploadFailed() }) {
            uploadReportImageUseCase(
                UploadReportImageParam(
                    bitmap = bitmap,
                    imageUri = imageUri,
                    id = id
                )
            ).collectLatest {
                onReportUploadedSuccessfully()
            }
        }

    private fun onUploadFailed() {
        updateEffects(UploadEffects.ShowUploadError(UIError.getUnexpectedError().msg))
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
            )
    }

    private fun onReportUploadedSuccessfully() {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
            )

        updateEffects(UploadEffects.ShowUploadSuccess(R.string.uploadReportSuccessfully))
    }

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true)
    }

    fun showError(message: Int? = null) {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = message
            )
    }

}