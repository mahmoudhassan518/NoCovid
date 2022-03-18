package com.ksa.unticovid.modules.analytics.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.analytics.data.model.mapper.toEntity
import com.ksa.unticovid.modules.analytics.data.source.AnalyzingRemoteSource
import com.ksa.unticovid.modules.analytics.domain.entity.AnalyticsEntity
import com.ksa.unticovid.modules.analytics.domain.repository.AnalyticsRepository
import com.ksa.unticovid.modules.common.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val remoteSource: AnalyzingRemoteSource
) :
    AnalyticsRepository, BaseRepository(ioDispatcher) {

    override fun getAnalytics(): Flow<List<AnalyticsEntity>> =
        requestHandler {
            remoteSource.getAnalytics().map { it.toEntity() }
        }
}