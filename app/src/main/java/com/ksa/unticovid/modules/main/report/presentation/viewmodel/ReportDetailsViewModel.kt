package com.ksa.unticovid.modules.main.report.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.main.report.domain.interactor.GetReportDetailsUseCase
import com.ksa.unticovid.modules.main.report.presentation.model.ReportDetailsUIModel
import com.ksa.unticovid.modules.main.report.presentation.model.ReportEffects
import com.ksa.unticovid.modules.main.report.presentation.model.ReportPCRUIModel
import com.ksa.unticovid.modules.main.report.presentation.model.mapper.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportDetailsViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val getReportDetailsUseCase: GetReportDetailsUseCase
) : BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(ReportDetailsUIModel())
    val uiState: StateFlow<ReportDetailsUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<ReportEffects>(replay = 0)
    val uiEffects: SharedFlow<ReportEffects> = _uiEffects

    private fun updateEffects(effect: ReportEffects) =
        viewModelScope.launch { _uiEffects.emit(effect) }

    fun updateCurrentReportPCR(it: ReportPCRUIModel) {
        if (it != uiState.value.currentReportPCR)
            _uiState.value =
                _uiState.value.copy(
                    currentReportPCR = it
                )
    }

    fun getReportDetails(reportId: String) =
        launchBlock(onStart = { donOnStart() }, onError = { showError() }) {
            getReportDetailsUseCase(reportId).collectLatest { report ->
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        report = report.toUIModel()
                    )
            }
        }

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true, errorMessage = null)
    }

    private fun showError() {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = if (_uiState.value.report == null) R.string.reportDetailsErrorMessage else null
            )

        if (_uiState.value.report != null)
            updateEffects(ReportEffects.ShowReportError(UIError.getUnexpectedError().msg))

    }


}
