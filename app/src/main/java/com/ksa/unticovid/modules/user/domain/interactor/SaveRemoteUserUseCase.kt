package com.ksa.unticovid.modules.user.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user.domain.entity.param.UserParam
import com.ksa.unticovid.modules.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class SaveRemoteUserUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<UserParam, UserEntity>() {

    override fun invoke(param: UserParam): Flow<UserEntity> =
        repository.saveRemoteUser(param).flatMapLatest {
            repository.saveLocalUser(it).flatMapLatest {
                repository.getLocalUser()
            }
        }
}
