package com.ksa.unticovid.modules.main.report.presentation.model

import androidx.annotation.StringRes

data class ReportUIModel(
    val reports: List<ReportItemUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val emptyMessage: Int? = null
)

data class ReportItemUIModel(
    val id: String,
    val hasCovid: Boolean,
    @StringRes val resultMessage: Int,
    val date: String,
    val covidData: ReportCovidDataUIModel
)


data class ReportCovidDataUIModel(
    val doctorId: String?,
    val doctorName: String?,
    val doctorMobile: String?,
    val doctorAddress: String?,
    val pcrList: List<ReportPCRUIModel>?
)


data class ReportPCRUIModel(val id: String, val testId: String, val image: String)