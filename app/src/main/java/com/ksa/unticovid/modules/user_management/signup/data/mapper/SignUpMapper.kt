package com.ksa.unticovid.modules.user_management.signup.data.mapper

import com.ksa.unticovid.modules.user_management.signup.data.SignUpRequest
import com.ksa.unticovid.modules.user_management.signup.domain.entity.param.SignUpParam

fun SignUpParam.toRequest() = SignUpRequest(
    identity = identity,
    email = email,
    password = password,
    age = age,
    mobile = mobile,
    address = address,
    gender = gender,
    name = name,
)