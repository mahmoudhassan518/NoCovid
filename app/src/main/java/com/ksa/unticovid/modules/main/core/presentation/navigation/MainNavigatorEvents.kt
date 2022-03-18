package com.ksa.unticovid.modules.main.core.presentation.navigation

sealed class MainNavigatorEvents {

    object OpenInformationScreen : MainNavigatorEvents()
    object OpenFactionScreen : MainNavigatorEvents()
    object OpenQuestionsScreen : MainNavigatorEvents()
    object OpenAnalyticsScreen : MainNavigatorEvents()
}
