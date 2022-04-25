package com.ksa.unticovid.core.navigation

interface FeaturesNavigator {
    fun openUserManagementScreen()
    fun openHomeScreen()
    fun openInformationScreen()
    fun openFactionScreen()
    fun openQuestionsScreen()
    fun openReportDetailsScreen(id: String)
}
