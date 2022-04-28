package com.ksa.unticovid.modules.questions.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseViewModel
import com.ksa.unticovid.core.extentions.getGenderFromString
import com.ksa.unticovid.modules.core.di.MainDispatcher
import com.ksa.unticovid.modules.information.presentation.model.InformationEffects
import com.ksa.unticovid.modules.questions.domain.interactor.SubmitQuestionsUseCase
import com.ksa.unticovid.modules.questions.presentation.model.QuestionsEffects
import com.ksa.unticovid.modules.questions.presentation.model.QuestionsUIModel
import com.ksa.unticovid.modules.questions.presentation.model.mapper.toParam
import com.ksa.unticovid.modules.questions.presentation.model.mapper.toUIModel
import com.ksa.unticovid.modules.user_management.user.domain.interactor.GetLocalUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val submitQuestionsUseCase: SubmitQuestionsUseCase
) : BaseViewModel(mainDispatcher) {

    private val _uiState = MutableStateFlow(QuestionsUIModel())
    val uiState: StateFlow<QuestionsUIModel> = _uiState

    private val _uiEffects = MutableSharedFlow<QuestionsEffects>(replay = 0)
    val uiEffects: SharedFlow<QuestionsEffects> = _uiEffects

    init {
        getUserData()
    }

    private fun updateEffects(effect: QuestionsEffects) = viewModelScope.launch {
        _uiEffects.emit(effect)
    }

    private fun getUserData() {
        launchBlock {
            getLocalUserUseCase(Unit).collectLatest {
                _uiState.value =
                    _uiState.value.copy(gender = it.gender.getGenderFromString(), age = it.age)
            }
        }
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
        updateEffects(QuestionsEffects.ShowQuestionsError(R.string.msgSomethingWentWrong))
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
