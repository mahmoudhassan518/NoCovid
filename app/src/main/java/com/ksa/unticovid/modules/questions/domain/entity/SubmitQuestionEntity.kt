package com.ksa.unticovid.modules.questions.domain.entity

data class SubmitQuestionEntity(
    val clear: Boolean,
    val message: String,
    val doctorId: String?,
    val doctorName: String?,
    val doctorMobile: String?,
    val doctorAddress: String?
)