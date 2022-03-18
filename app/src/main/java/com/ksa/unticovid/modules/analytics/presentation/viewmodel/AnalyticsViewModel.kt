package com.ksa.unticovid.modules.analytics.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.analytics.domain.interactor.GetAnalyticsUseCase
import com.ksa.unticovid.modules.analytics.presentation.model.AnalyticsEffects
import com.ksa.unticovid.modules.analytics.presentation.model.AnalyticsUIModel
import com.ksa.unticovid.modules.analytics.presentation.model.toUIModel
import com.ksa.unticovid.modules.common.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val getAnalyticsUseCase: GetAnalyticsUseCase
) : BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(AnalyticsUIModel())
    val uiState: StateFlow<AnalyticsUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<AnalyticsEffects>(replay = 0)
    val uiEffects: SharedFlow<AnalyticsEffects> = _uiEffects


    fun getAnalytics() = launchBlock(onStart = { donOnStart() },
        onError = { showError(R.string.analyticsErrorMessage) }) {
        getAnalyticsUseCase(Unit).collectLatest { analytics ->
            _uiState.value =
                _uiState.value.copy(
                    isLoading = false,
                    emptyMessage = buildEmptyMessage(analytics.isNullOrEmpty()),
                    analytics = analytics.map { it.toUIModel() })
        }
    }

    fun uploadNewAnalytics() {

    }

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true, errorMessage = null, emptyMessage = null)
    }

    private fun showError(message: Int) {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = if (_uiState.value.analytics.isNullOrEmpty()) message else null
            )

        if (!_uiState.value.analytics.isNullOrEmpty())
            viewModelScope.launch {
                _uiEffects.emit(AnalyticsEffects.ShowAnalyticsError(message))
            }
    }

    private fun buildEmptyMessage(isEmpty: Boolean) =
        if (isEmpty) R.string.emptyAnalyticsMessage else null

}