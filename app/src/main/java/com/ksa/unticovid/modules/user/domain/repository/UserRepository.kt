package com.ksa.unticovid.modules.user.domain.repository

import com.ksa.unticovid.modules.user.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun saveUser(userEntity: UserEntity): Flow<Unit>
    fun deleteUser(): Flow<Unit>
    fun getLocalUser(): Flow<UserEntity>
    fun getRemoteUser(id: String): Flow<UserEntity>
}
