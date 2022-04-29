package com.ksa.unticovid.modules.family.data.model

import com.google.gson.annotations.SerializedName

data class AddFamilyMemberRequest(

    @SerializedName("identity")
    val identity: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("age")
    val age: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("relation_type")
    val relation: String,
    @SerializedName("test_id")
    val reportId: String,
)
