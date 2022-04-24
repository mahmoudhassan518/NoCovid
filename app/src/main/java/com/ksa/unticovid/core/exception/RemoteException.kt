package com.ksa.unticovid.core.exception

data class RemoteException(val value: String) : Throwable(value)
