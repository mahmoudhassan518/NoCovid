package com.ksa.unticovid.modules.main.report.domain.repository

import com.ksa.unticovid.modules.main.report.domain.entity.ReportEntity
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun getReports(): Flow<List<ReportEntity>>
    fun getReportDetails(id: String): Flow<ReportEntity>
}
