package com.ksa.unticovid.core.navigation

interface NavigationCoordinator<T> {

    fun onStart(param: Any?)
    fun onEvent(event: T)
}
