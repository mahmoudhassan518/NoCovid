package com.ksa.unticovid.modules.questions.domain.entity

data class SubmitQuestionsParam(
    val gender: String? = null,
    val age: String? = null,
    val hasDyspnea: Boolean,
    val hasFever: Boolean,
    val hasCough: Boolean,
    val hasDiarrhea: Boolean,
)