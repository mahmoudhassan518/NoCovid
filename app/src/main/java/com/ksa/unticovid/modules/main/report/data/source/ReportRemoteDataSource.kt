package com.ksa.unticovid.modules.main.report.data.source

import com.ksa.unticovid.modules.common.data.source.remote.ApiService
import javax.inject.Inject

class ReportRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getUserReports() = apiService.getUserReports()
}
