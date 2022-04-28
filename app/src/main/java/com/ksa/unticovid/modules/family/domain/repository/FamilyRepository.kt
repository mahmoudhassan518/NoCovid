package com.ksa.unticovid.modules.family.domain.repository

import com.ksa.unticovid.modules.family.domain.enitiy.FamilyEntity
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam
import kotlinx.coroutines.flow.Flow

interface FamilyRepository {

    fun addFamilyMember(param: AddFamilyMemberParam): Flow<Unit>
    fun getFamilyMembers(): Flow<List<FamilyEntity>>
}
