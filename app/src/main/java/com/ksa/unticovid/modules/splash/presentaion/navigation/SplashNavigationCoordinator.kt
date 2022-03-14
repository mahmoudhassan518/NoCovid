package com.ksa.unticovid.modules.splash.presentaion.navigation

import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import javax.inject.Inject

class SplashNavigationCoordinator @Inject constructor(
    private val featuresNavigator: FeaturesNavigator
) : NavigationCoordinator<SplashNavigatorEvents> {

    override fun onStart(param: Any?) {
    }

    override fun onEvent(event: SplashNavigatorEvents) {
        when (event) {
            is SplashNavigatorEvents.OpenHomeScreen ->
                featuresNavigator.openHomeScreen()

            is SplashNavigatorEvents.OpenUserManagementScreen ->
                featuresNavigator.openUserManagementScreen()
        }
    }
}
