package com.ksa.unticovid.modules.main.report.data.model

import com.google.gson.annotations.SerializedName
import com.ksa.unticovid.modules.core.data.model.BaseResponse

data class ReportResponse(
    @SerializedName("data")
    val date: ArrayList<ReportDataResponse> = arrayListOf()
) : BaseResponse()

data class ReportDataResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("covid_status") var covidStatus: Boolean,
    @SerializedName("doctor") var doctor: ReportDoctorResponse?= null,
    @SerializedName("createAt") var createAt: String,
    @SerializedName("pcrs") var pcrs: ArrayList<ReportPCRResponse>? = arrayListOf()
)

data class ReportPCRResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("test_id") var testId: String,
    @SerializedName("image") var image: String
)

data class ReportDoctorResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("mobile") var mobile: String,
    @SerializedName("address") var address: String
)