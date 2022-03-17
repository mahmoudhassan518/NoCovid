package com.ksa.unticovid.core.exception

data class EmptyFieldsException(val value: String) : Throwable(value)
