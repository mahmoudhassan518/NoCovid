package com.ksa.unticovid.modules.common.di

import android.content.Context
import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.modules.common.presentation.navigation.FeaturesNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    @ActivityScoped
    fun provideFeaturesNavigator(@ActivityContext context: Context): FeaturesNavigator =
        FeaturesNavigatorImpl(context)
}
