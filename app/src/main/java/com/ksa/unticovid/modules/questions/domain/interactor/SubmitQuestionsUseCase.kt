package com.ksa.unticovid.modules.questions.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionEntity
import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionsParam
import com.ksa.unticovid.modules.questions.domain.repository.QuestionsRepository
import com.ksa.unticovid.modules.user_management.user.domain.interactor.GetLocalUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class SubmitQuestionsUseCase @Inject constructor(
    private val repository: QuestionsRepository,
    private val getLocalUserUseCase: GetLocalUserUseCase
) : BaseUseCase<SubmitQuestionsParam, SubmitQuestionEntity>() {

    override fun invoke(param: SubmitQuestionsParam): Flow<SubmitQuestionEntity> =
        getLocalUserUseCase(Unit).flatMapLatest {
            repository.submitQuestions(
                param.copy(
                    age = it.age,
                    gender = it.gender
                )
            )
        }
}