package com.ksa.unticovid.modules.family.data.source

import com.ksa.unticovid.modules.core.data.source.remote.ApiService
import com.ksa.unticovid.modules.family.data.model.AddFamilyMemberRequest
import javax.inject.Inject

class FamilyRemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun addFamilyMember(memberRequest: AddFamilyMemberRequest) =
        apiService.addFamilyMember(memberRequest)

    suspend fun getFamilyMembers(id: String) = apiService.getFamilyMembers(id)
}
