package com.ksa.unticovid.modules.user.data.model.mapper

import com.ksa.unticovid.modules.user.data.model.UserRequest
import com.ksa.unticovid.modules.user.data.model.UserResponse
import com.ksa.unticovid.modules.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user.domain.entity.param.UserParam

fun UserResponse.toEntity() = UserEntity(
    id = id,
    nationalId = nationalId,
    phoneNumber = phoneNumber,
    name = name,
    email = email,
    password = password
)

fun UserParam.toRequest() = UserRequest(
    nationalId = nationalId,
    phoneNumber = phoneNumber,
    name = name,
)
