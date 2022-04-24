package com.ksa.unticovid.core.navigation

import com.ksa.unticovid.modules.main.report.presentation.model.ReportCovidDataUIModel

interface FeaturesNavigator {
    fun openUserManagementScreen()
    fun openHomeScreen()
    fun openInformationScreen()
    fun openFactionScreen()
    fun openQuestionsScreen()
    fun openReportDetailsScreen(item: ReportCovidDataUIModel)
}
