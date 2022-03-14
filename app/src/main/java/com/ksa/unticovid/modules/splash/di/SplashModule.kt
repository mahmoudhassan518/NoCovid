package com.ksa.unticovid.modules.splash.di

import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.modules.splash.presentaion.navigation.SplashNavigationCoordinator
import com.ksa.unticovid.modules.splash.presentaion.navigation.SplashNavigatorEvents
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class SplashModule {

    @Provides
    @ActivityScoped
    fun provideHomeNavigationCoordinator(

        featuresNavigator: FeaturesNavigator
    ): NavigationCoordinator<SplashNavigatorEvents> =
        SplashNavigationCoordinator(
            featuresNavigator = featuresNavigator,
        )
}
