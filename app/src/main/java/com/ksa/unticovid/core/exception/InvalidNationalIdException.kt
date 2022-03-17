package com.ksa.unticovid.core.exception

data class InvalidNationalIdException(val value: String) : Throwable(value)
