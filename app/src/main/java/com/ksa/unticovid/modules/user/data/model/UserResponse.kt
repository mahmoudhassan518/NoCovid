package com.ksa.unticovid.modules.user.data.model

data class UserResponse(
    val id: String,
    val nationalId: String,
    val phoneNumber: String,
    val name: String,
    val email: String,
    val password: String?
)
