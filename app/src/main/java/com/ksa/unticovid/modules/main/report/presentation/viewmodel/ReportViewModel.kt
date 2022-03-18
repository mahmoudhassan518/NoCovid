package com.ksa.unticovid.modules.main.report.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.common.di.MainDispatcher
import com.ksa.unticovid.modules.main.report.domain.interactor.GetUserOrdersUseCase
import com.ksa.unticovid.modules.main.report.presentation.model.ReportListEffects
import com.ksa.unticovid.modules.main.report.presentation.model.ReportUIModel
import com.ksa.unticovid.modules.main.report.presentation.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val getUserOrdersUseCase: GetUserOrdersUseCase
) :
    BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(ReportUIModel())
    val uiState: StateFlow<ReportUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<ReportListEffects>(replay = 0)
    val uiEffects: SharedFlow<ReportListEffects> = _uiEffects

    fun getUserReports() = launchBlock(onStart = { donOnStart() }, onError = { showError() }) {
        getUserOrdersUseCase(Unit).collectLatest { reports ->
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
                _uiEffects.emit(ReportListEffects.ShowReportListError(R.string.reportErrorMessage))
            }
    }
}
