package com.ksa.unticovid.modules.user_management.user.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user_management.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRemoteUserDataUseCase @Inject constructor(
    private val repository: UserRepository,
    private val saveLocalUserUseCase: SaveLocalUserUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase
) : BaseUseCase<Unit, UserEntity>() {

    override fun invoke(param: Unit): Flow<UserEntity> =
        getLocalUserUseCase(Unit).flatMapLatest {
            repository.getRemoteUser().flatMapLatest { newUserEntity ->
                saveLocalUserUseCase(
                    newUserEntity.copy(
                        accessToken = it.accessToken,
                        tokenType = it.tokenType
                    )
                ).flatMapLatest {
                    getLocalUserUseCase(Unit)
                }
            }
        }
}
