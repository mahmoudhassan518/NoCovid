package com.ksa.unticovid.modules.questions.data.model

import com.ksa.unticovid.modules.core.data.model.BaseResponse

data class SubmitQuestionResponse(val data: SubmitQuestionDataResponse) : BaseResponse()

data class SubmitQuestionDataResponse(val clear: Boolean, val doctor: SubmitQuestionDoctorResponse?)

data class SubmitQuestionDoctorResponse(
    val id: String,
    val name: String,
    val mobile: String,
    val address: String,
)