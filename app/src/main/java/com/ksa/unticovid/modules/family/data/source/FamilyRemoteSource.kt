package com.ksa.unticovid.modules.family.data.source

import com.ksa.unticovid.modules.core.data.source.remote.ApiService
import com.ksa.unticovid.modules.family.data.model.SubmitFamilyMemberRequest
import javax.inject.Inject

class FamilyRemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun submitFamilyMember(memberRequest: SubmitFamilyMemberRequest) =
        apiService.submitFamilyMember(memberRequest)

    suspend fun getFamilyMembers() = apiService.getFamilyMembers()
}
