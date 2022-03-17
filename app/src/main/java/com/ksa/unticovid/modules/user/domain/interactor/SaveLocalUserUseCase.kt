package com.ksa.unticovid.modules.user.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveLocalUserUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<UserEntity, Unit>() {

    override fun invoke(param: UserEntity): Flow<Unit> =
        repository.saveLocalUser(param)
}
