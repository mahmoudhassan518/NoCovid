package com.ksa.unticovid.modules.user_management.user.data.source

import com.ksa.unticovid.modules.core.data.source.remote.ApiService
import com.ksa.unticovid.modules.user_management.user.data.model.UserRequest
import javax.inject.Inject

class UserRemoteSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getUserData() = apiService.getUserData()
    suspend fun saveUserData(user: UserRequest) = apiService.saveUserData(user)
}
