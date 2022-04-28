package com.ksa.unticovid.modules.family.domain.param

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.family.domain.enitiy.FamilyEntity
import com.ksa.unticovid.modules.family.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFamilyMembersUseCase @Inject constructor(
    private val repository: FamilyRepository,
) : BaseUseCase<Unit, List<FamilyEntity>>() {

    override fun invoke(param: Unit): Flow<List<FamilyEntity>> =
        repository.getFamilyMembers()
}
