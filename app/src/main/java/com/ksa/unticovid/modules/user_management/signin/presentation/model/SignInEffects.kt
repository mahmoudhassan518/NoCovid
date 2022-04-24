package com.ksa.unticovid.modules.user_management.signin.presentation.model

import com.ksa.unticovid.core.utils.UIError

sealed class SignInEffects {
    data class ShowResourceError(val error: UIError) : SignInEffects()
    data class ShowRemoteError(val error: String) : SignInEffects()
    object NavigateToMainScreen : SignInEffects()
}
