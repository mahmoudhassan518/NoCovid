package com.ksa.unticovid.modules.splash.presentaion.navigation


sealed class SplashNavigatorEvents {

    object OpenHomeScreen : SplashNavigatorEvents()
    object OpenUserManagementScreen : SplashNavigatorEvents()
}
