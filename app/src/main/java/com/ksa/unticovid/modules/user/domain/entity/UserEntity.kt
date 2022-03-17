package com.ksa.unticovid.modules.user.domain.entity

data class UserEntity(
    val id: String,
    val nationalId: String,
    val phoneNumber: String,
    val name: String,
    val email: String?,
    val password: String?,
)
