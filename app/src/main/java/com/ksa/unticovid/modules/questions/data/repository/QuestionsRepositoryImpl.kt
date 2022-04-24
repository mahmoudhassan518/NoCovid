package com.ksa.unticovid.modules.questions.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.core.di.IODispatcher
import com.ksa.unticovid.modules.questions.data.model.mapper.toEntity
import com.ksa.unticovid.modules.questions.data.model.mapper.toRequest
import com.ksa.unticovid.modules.questions.data.source.QuestionsRemoteSource
import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionEntity
import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionsParam
import com.ksa.unticovid.modules.questions.domain.repository.QuestionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val source: QuestionsRemoteSource
) : QuestionsRepository, BaseRepository(dispatcher) {

    override fun submitQuestions(param: SubmitQuestionsParam): Flow<SubmitQuestionEntity> =
        requestHandler {
            val response = source.submitQuestions(param.toRequest())
            response.toEntity(response.message)
        }
}