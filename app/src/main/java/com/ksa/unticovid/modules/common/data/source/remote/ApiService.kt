package com.ksa.unticovid.modules.common.data.source.remote

import com.ksa.unticovid.modules.analytics.data.model.AnalyticsResponse
import com.ksa.unticovid.modules.faction.data.model.FactionsResponse
import com.ksa.unticovid.modules.information.data.model.InformationResponse
import com.ksa.unticovid.modules.main.report.data.model.ReportResponse
import com.ksa.unticovid.modules.user.data.model.UserRequest
import com.ksa.unticovid.modules.user.data.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/api/reports")
    suspend fun getUserReports(): List<ReportResponse>

    @GET("/api/user")
    suspend fun getUserData(): UserResponse

    @POST("/api/user")
    suspend fun saveUserData(@Body user: UserRequest): UserResponse

    @GET("/api/user")
    suspend fun getFactions(): FactionsResponse

    @GET("/api/user")
    suspend fun getInformation(): InformationResponse

    @GET("/api/user")
    suspend fun getAnalytics(/*@Query("id") id: String**/): List<AnalyticsResponse>
}
