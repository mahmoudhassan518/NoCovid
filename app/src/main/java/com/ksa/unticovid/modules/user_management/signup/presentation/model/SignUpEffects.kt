package com.ksa.unticovid.modules.user_management.signup.presentation.model

import com.ksa.unticovid.core.utils.UIError

sealed class SignUpEffects {
    data class ShowResourceError(val error: UIError) : SignUpEffects()
    data class ShowRemoteError(val error: String) : SignUpEffects()
    object NavigateToMainScreen : SignUpEffects()
}
