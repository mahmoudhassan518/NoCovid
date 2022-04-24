package com.ksa.unticovid.modules.questions.data.source

import com.ksa.unticovid.modules.core.data.source.remote.ApiService
import com.ksa.unticovid.modules.questions.data.model.SubmitQuestionsRequest
import javax.inject.Inject

class QuestionsRemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun submitQuestions(request: SubmitQuestionsRequest) =
        apiService.submitUserQuestions(request)
}