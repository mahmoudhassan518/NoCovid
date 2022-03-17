package com.ksa.unticovid.modules.main.report.domain.repository

import com.ksa.unticovid.modules.main.report.domain.entity.ReportEntity
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun getUserReports(): Flow<List<ReportEntity>>
}
