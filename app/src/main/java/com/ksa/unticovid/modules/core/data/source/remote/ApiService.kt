package com.ksa.unticovid.modules.core.data.source.remote

import com.ksa.unticovid.modules.core.data.model.BaseResponse
import com.ksa.unticovid.modules.faction.data.model.FactionsResponse
import com.ksa.unticovid.modules.family.data.model.FamilyResponse
import com.ksa.unticovid.modules.family.data.model.AddFamilyMemberRequest
import com.ksa.unticovid.modules.family.data.model.AddFamilyMemberResponse
import com.ksa.unticovid.modules.information.data.model.InformationResponse
import com.ksa.unticovid.modules.main.report.data.model.ReportDetailsResponse
import com.ksa.unticovid.modules.main.report.data.model.ReportResponse
import com.ksa.unticovid.modules.questions.data.model.SubmitQuestionResponse
import com.ksa.unticovid.modules.questions.data.model.SubmitQuestionsRequest
import com.ksa.unticovid.modules.user_management.signin.data.model.SignInRequest
import com.ksa.unticovid.modules.user_management.signup.data.SignUpRequest
import com.ksa.unticovid.modules.user_management.user.data.model.UserRequest
import com.ksa.unticovid.modules.user_management.user.data.model.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Field

interface ApiService {

    @POST("login")
    suspend fun signIn(@Body request: SignInRequest): UserResponse

    @POST("register")
    suspend fun signUp(@Body request: SignUpRequest): UserResponse

    @POST("covid-tests")
    suspend fun getReports(): ReportResponse

    @FormUrlEncoded
    @POST("covid-test-details")
    suspend fun getReportDetails(@Field("test_id") id: String): ReportDetailsResponse

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

    @Multipart
    @POST("covid-test-pcr-attach")
    suspend fun uploadReportImage(
        @Part("test_id") test_id: RequestBody,
        @Part image: MultipartBody.Part,
    ): BaseResponse

    @POST("add-family-member")
    suspend fun addFamilyMember(@Body memberRequest: AddFamilyMemberRequest): AddFamilyMemberResponse

    @FormUrlEncoded
    @POST("family-members")
    suspend fun getFamilyMembers(@Field("test_id") id: String): FamilyResponse
}
