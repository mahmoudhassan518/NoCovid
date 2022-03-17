package com.ksa.unticovid.modules.main.profile.presentation.model

import com.ksa.unticovid.modules.user.domain.entity.UserEntity

data class ProfileUIModel(
    val user: UserEntity? = null,
    val isLoading: Boolean = false,
)
