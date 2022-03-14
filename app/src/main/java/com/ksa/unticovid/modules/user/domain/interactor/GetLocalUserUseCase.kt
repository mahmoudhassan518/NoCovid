package com.ksa.unticovid.modules.user.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetLocalUserUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<Unit, UserEntity>() {
    override fun invoke(param: Unit): Flow<UserEntity> =
        repository.getLocalUser()
}
