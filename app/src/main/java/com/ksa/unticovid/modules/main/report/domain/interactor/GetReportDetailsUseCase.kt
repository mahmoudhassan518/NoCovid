package com.ksa.unticovid.modules.main.report.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.main.report.domain.entity.ReportEntity
import com.ksa.unticovid.modules.main.report.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReportDetailsUseCase @Inject constructor(
    private val repository: ReportRepository,
) : BaseUseCase<String, ReportEntity>() {

    override fun invoke(param: String): Flow<ReportEntity> =
        repository.getReportDetails(param)
}
