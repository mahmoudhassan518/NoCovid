package com.ksa.unticovid.modules.main.report.presentation.model

import androidx.annotation.StringRes

sealed class ReportListEffects {
    data class ShowReportListError(@StringRes val message: Int) : ReportListEffects()
}
