package com.ksa.unticovid.modules.family.domain.enitiy

data class AddFamilyMemberParam(

    val identity: String,
    val gender: String,
    val age: String,
    val mobile: String,
    val name: String,
    val relation: String,
    val reportId: String,

)
