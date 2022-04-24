package com.ksa.unticovid.modules.main.core.presentation.navigation

import androidx.navigation.NavController
import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import javax.inject.Inject

class MainNavigationCoordinator @Inject constructor(
    private val navigator: MainFlowNavigator,
    private val featuresNavigator: FeaturesNavigator
) : NavigationCoordinator<MainNavigatorEvents> {

    private lateinit var navController: NavController

    override fun onStart(param: Any?) {
        navController = param as NavController
        navigator.navController = navController
    }

    override fun onEvent(event: MainNavigatorEvents) {
        when (event) {
            is MainNavigatorEvents.OpenInformationScreen ->
                featuresNavigator.openInformationScreen()
            is MainNavigatorEvents.OpenFactionScreen ->
                featuresNavigator.openFactionScreen()
            is MainNavigatorEvents.OpenQuestionsScreen ->
                featuresNavigator.openQuestionsScreen()
            is MainNavigatorEvents.OpenReportDetailsScreen ->
                featuresNavigator.openReportDetailsScreen(event.item)
        }
    }
}
