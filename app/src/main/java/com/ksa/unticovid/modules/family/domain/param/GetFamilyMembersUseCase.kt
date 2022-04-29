package com.ksa.unticovid.modules.family.domain.param

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.family.domain.enitiy.FamilyMemberEntity
import com.ksa.unticovid.modules.family.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFamilyMembersUseCase @Inject constructor(
    private val repository: FamilyRepository,
) : BaseUseCase<String, List<FamilyMemberEntity>>() {

    override fun invoke(param: String): Flow<List<FamilyMemberEntity>> =
        repository.getFamilyMembers(param)
}
