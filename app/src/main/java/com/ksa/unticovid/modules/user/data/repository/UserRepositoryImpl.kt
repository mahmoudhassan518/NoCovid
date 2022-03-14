package com.ksa.unticovid.modules.user.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.common.di.IODispatcher
import com.ksa.unticovid.modules.user.data.source.UserLocalSource
import com.ksa.unticovid.modules.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val localSource: UserLocalSource,
) : BaseRepository(ioDispatcher), UserRepository {
    override fun saveUser(userEntity: UserEntity): Flow<Unit> =
        requestHandler { localSource.saveUser(userEntity) }

    override fun deleteUser(): Flow<Unit> =
        requestHandler { localSource.deleteUser() }

    override fun getLocalUser(): Flow<UserEntity> =
        localSource.getUser()

    override fun getRemoteUser(id: String): Flow<UserEntity> {
        TODO("Not yet implemented")
    }
}
