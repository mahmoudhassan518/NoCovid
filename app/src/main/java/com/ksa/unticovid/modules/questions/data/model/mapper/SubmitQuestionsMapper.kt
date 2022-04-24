package com.ksa.unticovid.modules.questions.data.model.mapper

import com.ksa.unticovid.modules.questions.data.model.SubmitQuestionResponse
import com.ksa.unticovid.modules.questions.data.model.SubmitQuestionsRequest
import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionEntity
import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionsParam

fun SubmitQuestionsParam.toRequest() = SubmitQuestionsRequest(
    gender = gender,
    age = age,
    hasDyspnea = hasDyspnea,
    hasFever = hasFever,
    hasCough = hasCough,
    hasDiarrhea = hasDiarrhea
)

fun SubmitQuestionResponse.toEntity(message: String) = SubmitQuestionEntity(
    message = message,
    clear = data.clear,
    doctorId = data.doctor?.id,
    doctorName = data.doctor?.name,
    doctorMobile = data.doctor?.mobile,
    doctorAddress = data.doctor?.address,
)