package com.ksa.unticovid.modules.main.report.presentation.model

data class ReportUIModel(
    val reports: List<ReportItemUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)

data class ReportItemUIModel(val id: String, val title: String, val date: String)
