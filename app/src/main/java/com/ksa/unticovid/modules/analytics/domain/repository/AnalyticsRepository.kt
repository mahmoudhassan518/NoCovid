package com.ksa.unticovid.modules.analytics.domain.repository

import com.ksa.unticovid.modules.analytics.domain.entity.AnalyticsEntity
import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {

    fun getAnalytics(): Flow<List<AnalyticsEntity>>
}
