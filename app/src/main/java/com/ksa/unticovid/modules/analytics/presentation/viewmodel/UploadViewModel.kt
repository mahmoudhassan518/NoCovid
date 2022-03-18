package com.ksa.unticovid.modules.analytics.presentation.viewmodel

import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.analytics.domain.interactor.GetAnalyticsUseCase
import com.ksa.unticovid.modules.analytics.presentation.model.AnalyticsUIModel
import com.ksa.unticovid.modules.common.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val getAnalyticsUseCase: GetAnalyticsUseCase
) : BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(AnalyticsUIModel())
    val uiState: StateFlow<AnalyticsUIModel> = _uiState

    fun uploadImage() = launchBlock(onStart = { donOnStart() },
        onError = { showError(R.string.analyticsErrorMessage) }) {
        getAnalyticsUseCase(Unit).collectLatest {
            _uiState.value =
                _uiState.value.copy(
                    isLoading = false,
                    refresh = true
                )
        }
    }

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true, errorMessage = null, emptyMessage = null)
    }

    fun showError(message: Int?) {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = message
            )
    }

}