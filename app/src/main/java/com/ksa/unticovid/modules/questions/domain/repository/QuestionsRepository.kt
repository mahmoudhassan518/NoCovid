package com.ksa.unticovid.modules.questions.domain.repository

import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionEntity
import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionsParam
import kotlinx.coroutines.flow.Flow

interface QuestionsRepository {

    fun submitQuestions(param: SubmitQuestionsParam): Flow<SubmitQuestionEntity>
}