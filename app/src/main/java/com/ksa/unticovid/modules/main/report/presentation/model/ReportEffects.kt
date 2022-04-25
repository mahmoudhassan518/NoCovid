package com.ksa.unticovid.modules.main.report.presentation.model

import androidx.annotation.StringRes

sealed class ReportEffects {
    data class ShowReportError(@StringRes val message: Int) : ReportEffects()
}
