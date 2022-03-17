package com.ksa.unticovid.modules.main.core.di

import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.modules.main.core.presentation.navigation.MainFlowNavigator
import com.ksa.unticovid.modules.main.core.presentation.navigation.MainNavigationCoordinator
import com.ksa.unticovid.modules.main.core.presentation.navigation.MainNavigatorEvents
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class MainModule {

    @Provides
    @ActivityScoped
    fun provideMainNavigationCoordinator(
        navigator: MainFlowNavigator,
        featuresNavigator: FeaturesNavigator
    ): NavigationCoordinator<MainNavigatorEvents> =
        MainNavigationCoordinator(
            navigator = navigator,
            featuresNavigator = featuresNavigator
        )
}
