package com.ksa.unticovid.modules.questions.presentation.model

data class QuestionsUIModel(
    val question: QuestionsDataUIModel = QuestionsDataUIModel(),
    val gender: Int? = null,
    val age: String? = null,
    val isLoading: Boolean = false,
    val questionsResult: QuestionsResultUIModel? = null,
)

data class QuestionsDataUIModel(

    val hasDyspnea: Boolean = false,
    val hasFever: Boolean = false,
    val hasCough: Boolean = false,
    val hasDiarrhea: Boolean = false,
)

data class QuestionsResultUIModel(
    val message: String,
    val hasCovid: Boolean,
    val hasDoctorData: Boolean,
    val doctorId: String?,
    val doctorName: String?,
    val doctorMobile: String?,
    val doctorAddress: String?
)
