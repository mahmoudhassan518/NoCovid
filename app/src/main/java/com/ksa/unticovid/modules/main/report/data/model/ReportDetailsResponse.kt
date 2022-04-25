package com.ksa.unticovid.modules.main.report.data.model

import com.google.gson.annotations.SerializedName
import com.ksa.unticovid.modules.core.data.model.BaseResponse

data class ReportDetailsResponse(
    @SerializedName("data")
    val date: ReportDataResponse
) : BaseResponse()