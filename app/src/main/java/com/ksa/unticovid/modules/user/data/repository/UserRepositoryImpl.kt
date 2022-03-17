package com.ksa.unticovid.modules.user.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.common.di.IODispatcher
import com.ksa.unticovid.modules.user.data.model.mapper.toEntity
import com.ksa.unticovid.modules.user.data.model.mapper.toRequest
import com.ksa.unticovid.modules.user.data.source.UserLocalSource
import com.ksa.unticovid.modules.user.data.source.UserRemoteSource
import com.ksa.unticovid.modules.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user.domain.entity.param.UserParam
import com.ksa.unticovid.modules.user.domain.repository.UserRepository
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

    override fun getRemoteUser(id: String): Flow<UserEntity> = requestHandler {
        remoteSource.getUserData().toEntity()
    }

    override fun saveRemoteUser(user: UserParam): Flow<UserEntity> = requestHandler {
        remoteSource.saveUserData(user.toRequest()).toEntity()
    }
}
