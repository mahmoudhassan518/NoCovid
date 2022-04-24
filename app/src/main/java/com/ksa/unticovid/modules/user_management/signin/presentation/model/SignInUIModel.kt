package com.ksa.unticovid.modules.user_management.signin.presentation.model

import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity

data class SignInUIModel(
    val user: UserEntity? = null,
    val isLoading: Boolean = false,
)