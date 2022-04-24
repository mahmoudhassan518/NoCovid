package com.ksa.unticovid.modules.user_management.user.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.core.exception.EmptyFieldsException
import com.ksa.unticovid.core.exception.InvalidEmailException
import com.ksa.unticovid.core.extentions.isValidEmail
import com.ksa.unticovid.core.utils.ResponseContract
import com.ksa.unticovid.modules.user_management.signup.domain.entity.param.SignUpParam
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user_management.user.domain.entity.param.UserParam
import com.ksa.unticovid.modules.user_management.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SaveRemoteUserUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<UserParam, ResponseContract<UserEntity>>() {

    override fun invoke(param: UserParam): Flow<ResponseContract<UserEntity>> = with(param) {
        if (hasEmptyFields())
            throw EmptyFieldsException
        else if (!email.isValidEmail())
            throw InvalidEmailException
        saveRemoteData(this)
    }

    private fun UserParam.hasEmptyFields() =

                email.isEmpty() ||
                address.trim().isEmpty() ||
                name.isEmpty() ||
                age.isEmpty() ||
                address.isEmpty()
    private fun saveRemoteData(param: UserParam) =
        repository.saveRemoteUser(param).flatMapLatest { contract ->
            repository.saveLocalUser(contract.response).flatMapLatest {
                repository.getLocalUser().flatMapLatest {
                    flowOf(contract.copy(response = it))
                }
            }
        }
}
