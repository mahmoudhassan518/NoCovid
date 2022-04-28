package com.ksa.unticovid.modules.family.data.model

import com.google.gson.annotations.SerializedName
import com.ksa.unticovid.modules.core.data.model.BaseResponse

data class FamilyResponse(
    @SerializedName("data")
    val date: ArrayList<FamilyDataResponse> = arrayListOf()
) : BaseResponse()

data class FamilyDataResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("age") var age: String,
    @SerializedName("identity") var identity: String,
    @SerializedName("sex") var gender: String,
    @SerializedName("mobile") var mobile: String,
    @SerializedName("name") var name: String,
    @SerializedName("relation") var relation: String,

)
