package com.ksa.unticovid.modules.analytics.data.source

import com.ksa.unticovid.modules.common.data.source.remote.ApiService
import javax.inject.Inject

class AnalyzingRemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAnalytics() = apiService.getAnalytics()

}
