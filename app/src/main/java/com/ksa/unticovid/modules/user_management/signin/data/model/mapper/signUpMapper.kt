package com.ksa.unticovid.modules.user_management.signin.data.model.mapper

import com.ksa.unticovid.modules.user_management.signin.data.model.SignInRequest
import com.ksa.unticovid.modules.user_management.signin.domain.entity.param.SignInParam

fun SignInParam.toRequest() = SignInRequest(
    identity = identity,
    password = password
)