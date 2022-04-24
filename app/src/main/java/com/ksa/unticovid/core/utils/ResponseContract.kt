package com.ksa.unticovid.core.utils

data class ResponseContract<T>(
    val response: T,
    val message: String

)