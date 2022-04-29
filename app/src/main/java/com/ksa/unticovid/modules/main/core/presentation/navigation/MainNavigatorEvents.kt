package com.ksa.unticovid.modules.main.core.presentation.navigation

sealed class MainNavigatorEvents {

    object OpenInformationScreen : MainNavigatorEvents()
    object OpenFactionScreen : MainNavigatorEvents()
    object OpenQuestionsScreen : MainNavigatorEvents()
    data class OpenReportDetailsScreen(val id: String) : MainNavigatorEvents()
    data class OpenFamilyMembersScreen(val id: String) : MainNavigatorEvents()
    object SignOut : MainNavigatorEvents()
}
