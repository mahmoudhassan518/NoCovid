package com.ksa.unticovid.modules.user_management.core.presentation.navigation

import androidx.navigation.NavController
import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import javax.inject.Inject

class UserManagementNavigationCoordinator @Inject constructor(
    private val navigator: UserManagementFlowNavigator,
    private val featuresNavigator: FeaturesNavigator
) : NavigationCoordinator<UserManagementNavigatorEvents> {

    private lateinit var navController: NavController

    override fun onStart(param: Any?) {
        navController = param as NavController
        navigator.navController = navController
    }

    override fun onEvent(event: UserManagementNavigatorEvents) {
        when (event) {
            is UserManagementNavigatorEvents.OpenHomeScreen ->
                featuresNavigator.openHomeScreen()
            is UserManagementNavigatorEvents.OpenSignUpScreen ->
                navigator.showSignUpFragment()
            is UserManagementNavigatorEvents.OpenSignInScreen ->
                navigator.showSignInFragment()
        }
    }
}
