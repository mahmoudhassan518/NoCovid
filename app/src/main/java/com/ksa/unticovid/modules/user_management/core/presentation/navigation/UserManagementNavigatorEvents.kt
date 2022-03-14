package com.ksa.unticovid.modules.user_management.core.presentation.navigation

sealed class UserManagementNavigatorEvents {

    object OpenSignUpScreen : UserManagementNavigatorEvents()
    object OpenHomeScreen : UserManagementNavigatorEvents()
}
