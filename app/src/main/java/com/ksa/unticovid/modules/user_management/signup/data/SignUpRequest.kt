package com.ksa.unticovid.modules.user_management.signup.data

data class SignUpRequest(
    val identity: String,
    val email: String,
    val password: String,
    val gender: String,
    val age: String,
    val address: String,
    val mobile: String,
    val name: String,
)
