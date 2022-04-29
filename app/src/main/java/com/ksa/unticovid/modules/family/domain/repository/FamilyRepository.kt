package com.ksa.unticovid.modules.family.domain.repository

import com.ksa.unticovid.modules.family.domain.enitiy.FamilyMemberEntity
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam
import kotlinx.coroutines.flow.Flow

interface FamilyRepository {

    fun addFamilyMember(param: AddFamilyMemberParam): Flow<FamilyMemberEntity>
    fun getFamilyMembers(param: String): Flow<List<FamilyMemberEntity>>
}
