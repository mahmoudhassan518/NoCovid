package com.ksa.unticovid.modules.user_management.core.domain

import com.ksa.unticovid.modules.user_management.signin.domain.entity.param.SignInParam
import com.ksa.unticovid.modules.user_management.signup.domain.entity.param.SignUpParam
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserManagementRepository {

    fun signIn(param: SignInParam): Flow<UserEntity>
    fun signUp(param: SignUpParam): Flow<UserEntity>
}