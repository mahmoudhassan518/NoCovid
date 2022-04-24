package com.ksa.unticovid.modules.user_management.user.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.core.utils.ResponseContract
import com.ksa.unticovid.modules.core.di.IODispatcher
import com.ksa.unticovid.modules.user_management.user.data.model.mapper.toEntity
import com.ksa.unticovid.modules.user_management.user.data.model.mapper.toRequest
import com.ksa.unticovid.modules.user_management.user.data.source.UserLocalSource
import com.ksa.unticovid.modules.user_management.user.data.source.UserRemoteSource
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user_management.user.domain.entity.param.UserParam
import com.ksa.unticovid.modules.user_management.user.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val localSource: UserLocalSource,
    private val remoteSource: UserRemoteSource
) : BaseRepository(ioDispatcher), UserRepository {
    override fun saveLocalUser(userEntity: UserEntity): Flow<Unit> =
        requestHandler { localSource.saveUser(userEntity) }

    override fun deleteLocalUser(): Flow<Unit> =
        requestHandler { localSource.deleteUser() }

    override fun getLocalUser(): Flow<UserEntity> =
        localSource.getUser()

    override fun getRemoteUser(): Flow<UserEntity> = requestHandler {
        remoteSource.getUserData().toEntity()
    }

    override fun saveRemoteUser(user: UserParam): Flow<ResponseContract<UserEntity>> =
        requestHandler {
            val response = remoteSource.saveUserData(user.toRequest())
            ResponseContract(response.toEntity(), response.message)
        }

}
