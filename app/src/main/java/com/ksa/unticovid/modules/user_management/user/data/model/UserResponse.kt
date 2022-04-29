package com.ksa.unticovid.modules.user_management.user.data.model

import com.google.gson.annotations.SerializedName
import com.ksa.unticovid.modules.core.data.model.BaseResponse

data class UserResponse(
    val access_token: String,
    val token_type: String,
    val data: UserDataResponse,
) : BaseResponse()

data class UserDataResponse(

    val id: String,
    @SerializedName("user_type_id")
    val userTypeId: String,
    val identity: String,
    val mobile: String,
    val name: String,
    val email: String?,
    val age: String?,
    val gender: String,
    val address: String?,
    @SerializedName("covid_status")
    val covidStatus: String?,
)