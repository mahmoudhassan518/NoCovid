package com.ksa.unticovid.modules.family.data.model

import com.google.gson.annotations.SerializedName
import com.ksa.unticovid.modules.core.data.model.BaseResponse

data class AddFamilyMemberResponse(
    @SerializedName("data")
    var data: FamilyDataResponse
) : BaseResponse()