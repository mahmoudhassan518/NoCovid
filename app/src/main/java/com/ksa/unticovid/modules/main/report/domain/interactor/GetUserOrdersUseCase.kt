package com.ksa.unticovid.modules.main.report.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.main.report.domain.entity.ReportEntity
import com.ksa.unticovid.modules.main.report.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserOrdersUseCase @Inject constructor(
    private val repository: ReportRepository,
) : BaseUseCase<Unit, List<ReportEntity>>() {

    override fun invoke(param: Unit): Flow<List<ReportEntity>> =
        repository.getUserReports()
}
