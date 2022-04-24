package com.ksa.unticovid.modules.user_management.signup.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.core.exception.EmptyFieldsException
import com.ksa.unticovid.core.exception.InvalidEmailException
import com.ksa.unticovid.core.exception.PasswordLengthException
import com.ksa.unticovid.core.exception.PasswordMissMatchException
import com.ksa.unticovid.core.extentions.isValidEmail
import com.ksa.unticovid.modules.user_management.core.domain.UserManagementRepository
import com.ksa.unticovid.modules.user_management.signup.domain.entity.param.SignUpParam
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user_management.user.domain.interactor.GetLocalUserUseCase
import com.ksa.unticovid.modules.user_management.user.domain.interactor.SaveLocalUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: UserManagementRepository,
    private val saveLocalUserUseCase: SaveLocalUserUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase
) : BaseUseCase<SignUpParam, UserEntity>() {

    override fun invoke(param: SignUpParam): Flow<UserEntity> =
        with(param) {
            if (hasEmptyFields())
                throw EmptyFieldsException
            else if (!email.isValidEmail())
                throw InvalidEmailException
            else if (password.trim().length < 6)
                throw PasswordLengthException
            else if (password != confirmPassword)
                throw PasswordMissMatchException
            else
                signIn(this)
        }

    private fun SignUpParam.hasEmptyFields() =
        identity.isEmpty() ||
                name.isEmpty() ||
                email.isEmpty() ||
                password.trim().isEmpty() ||
                gender.isEmpty() ||
                age.isEmpty() ||
                address.isEmpty() ||
                mobile.isEmpty()

    private fun signIn(param: SignUpParam) =
        repository.signUp(param).flatMapLatest { newUserEntity ->
            saveLocalUserUseCase(newUserEntity).flatMapLatest {
                getLocalUserUseCase(Unit)
            }
        }
}