package com.ksa.unticovid.modules.main.core.presentation.navigation

import com.ksa.unticovid.modules.main.report.presentation.model.ReportCovidDataUIModel

sealed class MainNavigatorEvents {

    object OpenInformationScreen : MainNavigatorEvents()
    object OpenFactionScreen : MainNavigatorEvents()
    object OpenQuestionsScreen : MainNavigatorEvents()
    data class OpenReportDetailsScreen(val item: ReportCovidDataUIModel) : MainNavigatorEvents()
}
