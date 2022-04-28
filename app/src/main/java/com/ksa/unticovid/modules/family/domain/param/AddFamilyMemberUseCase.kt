package com.ksa.unticovid.modules.family.domain.param

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.core.exception.EmptyFieldsException
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam
import com.ksa.unticovid.modules.family.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFamilyMemberUseCase @Inject constructor(
    private val repository: FamilyRepository,
) : BaseUseCase<AddFamilyMemberParam, Unit>() {

    override fun invoke(param: AddFamilyMemberParam): Flow<Unit> =
        with(param) {
            if (hasEmptyFields())
                throw EmptyFieldsException
            else
                addFamilyMember(this)
        }

    private fun AddFamilyMemberParam.hasEmptyFields() =
        identity.trim().isEmpty() ||
            name.trim().isEmpty() ||
            relation.trim().isEmpty() ||
            gender.trim().isEmpty() ||
            age.trim().isEmpty() ||
            mobile.trim().isEmpty()

    private fun addFamilyMember(param: AddFamilyMemberParam) =
        repository.addFamilyMember(param)
}
