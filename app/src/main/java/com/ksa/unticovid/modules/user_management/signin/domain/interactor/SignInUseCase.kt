package com.ksa.unticovid.modules.user_management.signin.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.core.exception.EmptyFieldsException
import com.ksa.unticovid.modules.user_management.core.domain.UserManagementRepository
import com.ksa.unticovid.modules.user_management.signin.domain.entity.param.SignInParam
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user_management.user.domain.interactor.GetLocalUserUseCase
import com.ksa.unticovid.modules.user_management.user.domain.interactor.SaveLocalUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: UserManagementRepository,
    private val saveLocalUserUseCase: SaveLocalUserUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase
) : BaseUseCase<SignInParam, UserEntity>() {

    override fun invoke(param: SignInParam): Flow<UserEntity> =
        if (param.identity.isEmpty() || param.password.isEmpty())
            throw EmptyFieldsException
        else
            signIn(param)

    private fun signIn(param: SignInParam) =
        repository.signIn(param).flatMapLatest { newUserEntity ->
            saveLocalUserUseCase(newUserEntity).flatMapLatest {
                getLocalUserUseCase(Unit)
            }
        }
}