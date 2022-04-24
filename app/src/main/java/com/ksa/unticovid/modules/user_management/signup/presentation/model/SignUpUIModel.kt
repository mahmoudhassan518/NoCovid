package com.ksa.unticovid.modules.user_management.signup.presentation.model

import com.ksa.unticovid.modules.user_management.core.presentation.model.GenderSettings
import com.ksa.unticovid.modules.user_management.core.presentation.model.mapper.genderUISettings
import com.ksa.unticovid.modules.user_management.user.domain.entity.GenderType
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity

data class SignUpUIModel(
    val user: UserEntity? = null,
    val genderSettings: GenderSettings = GenderType.MALE.genderUISettings(),
    val currentGender: GenderType = GenderType.MALE,
    val isLoading: Boolean = false,
)