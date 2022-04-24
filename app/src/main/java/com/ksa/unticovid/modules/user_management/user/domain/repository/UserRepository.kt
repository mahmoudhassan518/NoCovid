package com.ksa.unticovid.modules.user_management.user.domain.repository

import com.ksa.unticovid.core.utils.ResponseContract
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user_management.user.domain.entity.param.UserParam
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun saveLocalUser(userEntity: UserEntity): Flow<Unit>
    fun deleteLocalUser(): Flow<Unit>
    fun getLocalUser(): Flow<UserEntity>
    fun getRemoteUser(): Flow<UserEntity>
    fun saveRemoteUser(user: UserParam): Flow<ResponseContract<UserEntity>>
}
