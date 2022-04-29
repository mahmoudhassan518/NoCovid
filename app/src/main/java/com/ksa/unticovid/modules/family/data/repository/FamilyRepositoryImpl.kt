package com.ksa.unticovid.modules.family.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.core.di.IODispatcher
import com.ksa.unticovid.modules.family.data.model.mapper.toEntity
import com.ksa.unticovid.modules.family.data.model.mapper.toRequest
import com.ksa.unticovid.modules.family.data.source.FamilyRemoteSource
import com.ksa.unticovid.modules.family.domain.enitiy.FamilyMemberEntity
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam
import com.ksa.unticovid.modules.family.domain.repository.FamilyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FamilyRepositoryImpl @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val source: FamilyRemoteSource
) : FamilyRepository, BaseRepository(dispatcher) {
    override fun addFamilyMember(param: AddFamilyMemberParam): Flow<FamilyMemberEntity> =
        requestHandler {
            source.addFamilyMember(param.toRequest()).data.toEntity()
        }

    override fun getFamilyMembers(param: String): Flow<List<FamilyMemberEntity>> = requestHandler {
        source.getFamilyMembers(param).data.map { it.toEntity() }
    }
}
