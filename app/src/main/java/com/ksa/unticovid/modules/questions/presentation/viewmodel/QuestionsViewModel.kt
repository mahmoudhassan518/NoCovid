package com.ksa.unticovid.modules.questions.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.information.presentation.model.InformationEffects
import com.ksa.unticovid.modules.questions.domain.interactor.SubmitQuestionsUseCase
import com.ksa.unticovid.modules.questions.presentation.model.QuestionsUIModel
import com.ksa.unticovid.modules.questions.presentation.model.mapper.toParam
import com.ksa.unticovid.modules.questions.presentation.model.mapper.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val submitQuestionsUseCase: SubmitQuestionsUseCase
) : BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(QuestionsUIModel())
    val uiState: StateFlow<QuestionsUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<InformationEffects>(replay = 0)
    val uiEffects: SharedFlow<InformationEffects> = _uiEffects

    private fun updateEffects(effect: InformationEffects) = viewModelScope.launch {
        _uiEffects.emit(effect)
    }

    fun submitQuestions() =
        launchBlock(onStart = { donOnStart() }, onError = { doOnError() }) {
            submitQuestionsUseCase(uiState.value.question.toParam()).collectLatest { data ->
                _uiState.value =
                    _uiState.value.copy(isLoading = false, questionsResult = data.toUIModel())
            }
        }

    private fun donOnStart() {
        _uiState.value =
            _uiState.value.copy(isLoading = true)
    }

    private fun doOnError() {
        _uiState.value = _uiState.value.copy(isLoading = false)
        updateEffects(InformationEffects.ShowInformationError(R.string.msgSomethingWentWrong))
    }

    fun updateFeverState(it: Boolean) {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                question = _uiState.value.question.copy(hasFever = it)
            )
    }

    fun updateDyspneaState(it: Boolean) {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                question = _uiState.value.question.copy(hasDyspnea = it)
            )
    }

    fun updateDiarrheaState(it: Boolean) {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                question = _uiState.value.question.copy(hasDiarrhea = it)
            )

    }

    fun updateCoughState(it: Boolean) {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                question = _uiState.value.question.copy(hasCough = it)
            )

    }
}
