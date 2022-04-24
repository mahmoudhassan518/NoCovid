package com.ksa.unticovid.modules.core.data.source.remote

import com.ksa.unticovid.modules.faction.data.model.FactionsResponse
import com.ksa.unticovid.modules.information.data.model.InformationResponse
import com.ksa.unticovid.modules.main.report.data.model.ReportResponse
import com.ksa.unticovid.modules.questions.data.model.SubmitQuestionResponse
import com.ksa.unticovid.modules.questions.data.model.SubmitQuestionsRequest
import com.ksa.unticovid.modules.user_management.signin.data.model.SignInRequest
import com.ksa.unticovid.modules.user_management.signup.data.SignUpRequest
import com.ksa.unticovid.modules.user_management.user.data.model.UserRequest
import com.ksa.unticovid.modules.user_management.user.data.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun signIn(@Body request: SignInRequest): UserResponse

    @POST("register")
    suspend fun signUp(@Body request: SignUpRequest): UserResponse

    @POST("covid-tests")
    suspend fun getUserReports(): ReportResponse

    @POST("profile")
    suspend fun getUserData(): UserResponse

    @POST("update-profile")
    suspend fun saveUserData(@Body user: UserRequest): UserResponse

    @POST("covid-face")
    suspend fun getFactions(): FactionsResponse

    @POST("covid-info")
    suspend fun getInformation(): InformationResponse

    @POST("covid-check-answers")
    suspend fun submitUserQuestions(@Body user: SubmitQuestionsRequest): SubmitQuestionResponse
}
