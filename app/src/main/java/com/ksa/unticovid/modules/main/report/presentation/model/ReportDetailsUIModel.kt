package com.ksa.unticovid.modules.main.report.presentation.model


data class ReportDetailsUIModel(
    val report: ReportItemUIModel? = null,
    val currentReportPCR: ReportPCRUIModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
)
