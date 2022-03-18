package com.ksa.unticovid.modules.analytics.di

import com.ksa.unticovid.modules.analytics.data.repository.AnalyticsRepositoryImpl
import com.ksa.unticovid.modules.analytics.domain.repository.AnalyticsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AnalyticsModule {

    @Binds
    internal abstract fun bindAnalyticsRepository(impl: AnalyticsRepositoryImpl): AnalyticsRepository
}
