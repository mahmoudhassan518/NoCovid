package com.ksa.unticovid.modules.user_management.signup.domain.entity.param

data class SignUpParam(
    val identity: String,
    val email: String,
    val name: String,
    val password: String,
    val confirmPassword: String? = null,
    val gender: String,
    val age: String,
    val address: String,
    val mobile: String,
)