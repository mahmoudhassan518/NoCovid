package com.ksa.unticovid.modules.user_management.core.di

import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.modules.user_management.core.presentation.navigation.UserManagementFlowNavigator
import com.ksa.unticovid.modules.user_management.core.presentation.navigation.UserManagementNavigationCoordinator
import com.ksa.unticovid.modules.user_management.core.presentation.navigation.UserManagementNavigatorEvents
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class UserManagementNavigationModule {

    @Provides
    @ActivityScoped
    fun provideUserManagementNavigationCoordinator(
        navigator: UserManagementFlowNavigator,
        featuresNavigator: FeaturesNavigator
    ): NavigationCoordinator<UserManagementNavigatorEvents> =
        UserManagementNavigationCoordinator(
            navigator = navigator,
            featuresNavigator = featuresNavigator
        )
}
