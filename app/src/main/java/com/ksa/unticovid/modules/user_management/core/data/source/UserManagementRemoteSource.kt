package com.ksa.unticovid.modules.user_management.core.data.source

import com.ksa.unticovid.modules.core.data.source.remote.ApiService
import com.ksa.unticovid.modules.user_management.signin.data.model.SignInRequest
import com.ksa.unticovid.modules.user_management.signup.data.SignUpRequest
import javax.inject.Inject

class UserManagementRemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun signIn(request: SignInRequest) = apiService.signIn(request)
    suspend fun signUp(request: SignUpRequest) = apiService.signUp(request)
}