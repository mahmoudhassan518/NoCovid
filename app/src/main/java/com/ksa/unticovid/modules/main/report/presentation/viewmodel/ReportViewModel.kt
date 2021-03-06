package com.ksa.unticovid.modules.main.report.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.main.report.domain.interactor.GetReportsUseCase
import com.ksa.unticovid.modules.main.report.presentation.model.ReportEffects
import com.ksa.unticovid.modules.main.report.presentation.model.ReportUIModel
import com.ksa.unticovid.modules.main.report.presentation.model.mapper.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val getReportsUseCase: GetReportsUseCase
) :
    BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(ReportUIModel())
    val uiState: StateFlow<ReportUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<ReportEffects>(replay = 0)
    val uiEffects: SharedFlow<ReportEffects> = _uiEffects

    fun getUserReports() = launchBlock(onStart = { donOnStart() }, onError = { showError() }) {
        getReportsUseCase(Unit).collectLatest { reports ->
            _uiState.value =
                _uiState.value.copy(
                    isLoading = false,
                    emptyMessage = buildEmptyMessage(reports.isNullOrEmpty()),
                    reports = reports.map { it.toUIModel() })
        }
    }

    private fun buildEmptyMessage(isEmpty: Boolean) =
        if (isEmpty) R.string.emptyReportMessage else null

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true, errorMessage = null, emptyMessage = null)
    }

    private fun showError() {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = if (_uiState.value.reports.isNullOrEmpty()) R.string.reportErrorMessage else null
            )

        if (!_uiState.value.reports.isNullOrEmpty())
            viewModelScope.launch {
                _uiEffects.emit(ReportEffects.ShowReportError(UIError.getUnexpectedError().msg))
            }
    }
}
