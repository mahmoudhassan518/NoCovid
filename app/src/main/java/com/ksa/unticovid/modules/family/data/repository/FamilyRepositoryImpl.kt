package com.ksa.unticovid.modules.family.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.core.di.IODispatcher
import com.ksa.unticovid.modules.family.data.model.mapper.toEntity
import com.ksa.unticovid.modules.family.data.model.mapper.toRequest
import com.ksa.unticovid.modules.family.data.source.FamilyRemoteSource
import com.ksa.unticovid.modules.family.domain.enitiy.FamilyEntity
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam
import com.ksa.unticovid.modules.family.domain.repository.FamilyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FamilyRepositoryImpl @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val source: FamilyRemoteSource
) : FamilyRepository, BaseRepository(dispatcher) {
    override fun addFamilyMember(param: AddFamilyMemberParam): Flow<Unit> = requestHandler {
        source.submitFamilyMember(param.toRequest())
    }

    override fun getFamilyMembers(): Flow<List<FamilyEntity>> = requestHandler {
        source.getFamilyMembers().date.map { it.toEntity() }
    }
}
