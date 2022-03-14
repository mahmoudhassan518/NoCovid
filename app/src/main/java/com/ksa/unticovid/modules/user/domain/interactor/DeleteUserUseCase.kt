package com.ksa.unticovid.modules.user.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UserRepository) :
    BaseUseCase<Unit, Unit>() {
    override fun invoke(param: Unit): Flow<Unit> {
        return repository.deleteUser()
    }
}
