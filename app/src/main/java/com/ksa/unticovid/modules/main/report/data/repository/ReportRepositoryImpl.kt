package com.ksa.unticovid.modules.main.report.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.common.di.IODispatcher
import com.ksa.unticovid.modules.main.report.data.model.mapper.toEntity
import com.ksa.unticovid.modules.main.report.data.source.ReportRemoteSource
import com.ksa.unticovid.modules.main.report.domain.entity.ReportEntity
import com.ksa.unticovid.modules.main.report.domain.repository.ReportRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val remoteSource: ReportRemoteSource
) :
    ReportRepository, BaseRepository(ioDispatcher) {
    override fun getUserReports(): Flow<List<ReportEntity>> =
        requestHandler { remoteSource.getUserReports().map { it.toEntity() } }
}
