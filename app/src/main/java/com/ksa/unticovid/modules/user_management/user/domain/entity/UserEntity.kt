package com.ksa.unticovid.modules.user_management.user.domain.entity

data class UserEntity(
    val accessToken: String?,
    val tokenType: String?,
    val id: String,
    val nationalId: String,
    val phoneNumber: String,
    val name: String,
    val email: String?,
    val address: String?,
    val age: String?,
    val gender: String,

)
