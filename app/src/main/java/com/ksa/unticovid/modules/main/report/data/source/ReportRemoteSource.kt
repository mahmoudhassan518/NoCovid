package com.ksa.unticovid.modules.main.report.data.source

import com.ksa.unticovid.modules.common.data.source.remote.ApiService
import javax.inject.Inject

class ReportRemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getUserReports() = apiService.getUserReports()
}
