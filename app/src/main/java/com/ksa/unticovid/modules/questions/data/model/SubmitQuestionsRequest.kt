package com.ksa.unticovid.modules.questions.data.model

import com.google.gson.annotations.SerializedName

class SubmitQuestionsRequest(
    @SerializedName("sex")
    val gender: String? = null,
    @SerializedName("age")
    val age: String? = null,
    @SerializedName("has_dyspnea")
    val hasDyspnea: Boolean,
    @SerializedName("has_fever")
    val hasFever: Boolean,
    @SerializedName("has_cough")
    val hasCough: Boolean,
    @SerializedName("diarrhea")
    val hasDiarrhea: Boolean,
)