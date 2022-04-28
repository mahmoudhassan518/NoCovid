package com.ksa.unticovid.modules.family.data.model

data class SubmitFamilyMemberRequest(

    val identity: String,
    val gender: String,
    val age: String,
    val mobile: String,
    val name: String,
    val relation: String,

)
