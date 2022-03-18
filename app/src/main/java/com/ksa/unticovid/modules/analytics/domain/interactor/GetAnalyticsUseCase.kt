package com.ksa.unticovid.modules.analytics.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.analytics.domain.entity.AnalyticsEntity
import com.ksa.unticovid.modules.analytics.domain.repository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnalyticsUseCase @Inject constructor(
    private val repository: AnalyticsRepository,
) : BaseUseCase<Unit, List<AnalyticsEntity>>() {

    override fun invoke(param: Unit): Flow<List<AnalyticsEntity>> =
        repository.getAnalytics()
}
