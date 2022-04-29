package com.ksa.unticovid.modules.family.presentation.model

import com.ksa.unticovid.modules.user_management.core.presentation.model.GenderSettings
import com.ksa.unticovid.modules.user_management.core.presentation.model.mapper.genderUISettings
import com.ksa.unticovid.modules.user_management.user.domain.entity.GenderType

data class AddFamilyMemberUIModel(

    val currentGender: GenderType = GenderType.MALE,
    val genderSettings: GenderSettings = GenderType.MALE.genderUISettings(),
    val currentFamilyMemberData: FamilyMemberDataUIModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
)
