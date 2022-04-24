package com.ksa.unticovid.modules.user_management.core.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.core.di.IODispatcher
import com.ksa.unticovid.modules.user_management.core.data.source.UserManagementRemoteSource
import com.ksa.unticovid.modules.user_management.core.domain.UserManagementRepository
import com.ksa.unticovid.modules.user_management.signin.data.model.mapper.toRequest
import com.ksa.unticovid.modules.user_management.signin.domain.entity.param.SignInParam
import com.ksa.unticovid.modules.user_management.signup.data.mapper.toRequest
import com.ksa.unticovid.modules.user_management.signup.domain.entity.param.SignUpParam
import com.ksa.unticovid.modules.user_management.user.data.model.mapper.toEntity
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserManagementRepositoryImpl @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val source: UserManagementRemoteSource
) :
    UserManagementRepository, BaseRepository(dispatcher) {
    override fun signIn(param: SignInParam): Flow<UserEntity> = requestHandler {
        source.signIn(param.toRequest()).toEntity()
    }

    override fun signUp(param: SignUpParam): Flow<UserEntity> = requestHandler {
        source.signUp(param.toRequest()).toEntity()
    }
}