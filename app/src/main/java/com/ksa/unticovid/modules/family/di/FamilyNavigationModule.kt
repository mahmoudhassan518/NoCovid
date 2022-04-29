package com.ksa.unticovid.modules.family.di

import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.modules.family.presentation.navigation.FamilyFlowNavigator
import com.ksa.unticovid.modules.family.presentation.navigation.FamilyNavigationCoordinator
import com.ksa.unticovid.modules.family.presentation.navigation.FamilyNavigatorEvents
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class FamilyNavigationModule {

    @Provides
    @ActivityScoped
    fun provideFamilyNavigationCoordinator(
        featuresNavigator: FeaturesNavigator,
        familyFlowNavigator: FamilyFlowNavigator
    ): NavigationCoordinator<FamilyNavigatorEvents> =
        FamilyNavigationCoordinator(
            featuresNavigator = featuresNavigator,
            familyFlowNavigator = familyFlowNavigator
        )
}
