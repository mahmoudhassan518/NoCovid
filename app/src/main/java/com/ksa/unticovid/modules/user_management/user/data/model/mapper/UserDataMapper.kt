package com.ksa.unticovid.modules.user_management.user.data.model.mapper

import com.ksa.unticovid.modules.user_management.user.data.model.UserRequest
import com.ksa.unticovid.modules.user_management.user.data.model.UserResponse
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity
import com.ksa.unticovid.modules.user_management.user.domain.entity.param.UserParam

fun UserResponse.toEntity() = UserEntity(
    accessToken = access_token,
    tokenType = token_type,
    id = data.id,
    nationalId = data.identity,
    phoneNumber = data.mobile,
    name = data.name,
    email = data.email,
    gender = data.gender,
    age = data.age,
    address = data.address,
    covidStatus = data.covidStatus
)

fun UserParam.toRequest() = UserRequest(
    email = email,
    address = address,
    age = age,
    mobile = phoneNumber,
    name = name,
)
