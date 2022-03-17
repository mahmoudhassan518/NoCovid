package com.ksa.unticovid.modules.main.report.di

import com.ksa.unticovid.modules.main.report.data.repository.ReportRepositoryImpl
import com.ksa.unticovid.modules.main.report.domain.repository.ReportRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReportModule {

    @Binds
    internal abstract fun bindReportRepository(impl: ReportRepositoryImpl): ReportRepository
}
