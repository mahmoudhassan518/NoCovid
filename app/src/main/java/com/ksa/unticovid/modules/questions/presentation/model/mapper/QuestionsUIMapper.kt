package com.ksa.unticovid.modules.questions.presentation.model.mapper

import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionEntity
import com.ksa.unticovid.modules.questions.domain.entity.SubmitQuestionsParam
import com.ksa.unticovid.modules.questions.presentation.model.QuestionsDataUIModel
import com.ksa.unticovid.modules.questions.presentation.model.QuestionsResultUIModel

fun QuestionsDataUIModel.toParam() = SubmitQuestionsParam(
    hasDyspnea = hasDyspnea,
    hasFever = hasFever, hasCough = hasCough, hasDiarrhea = hasDiarrhea
)

fun SubmitQuestionEntity.toUIModel() = QuestionsResultUIModel(
    message = message,
    hasCovid = clear,
    hasDoctorData = doctorId != null && doctorMobile != null,
    doctorId = doctorId,
    doctorAddress = doctorAddress,
    doctorName = doctorName,
    doctorMobile = doctorMobile ?: "0"
)