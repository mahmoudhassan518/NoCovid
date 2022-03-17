package com.ksa.unticovid.modules.user.data.source

import com.ksa.unticovid.modules.common.data.source.remote.ApiService
import com.ksa.unticovid.modules.user.data.model.UserRequest
import com.ksa.unticovid.modules.user.domain.entity.param.UserParam
import javax.inject.Inject

class UserRemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getUserData() = apiService.getUserData()
    suspend fun saveUserData(user: UserRequest) = apiService.saveUserData(user)
}
