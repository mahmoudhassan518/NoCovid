package com.ksa.unticovid.modules.main.profile.presentation.model

import androidx.annotation.StringRes
import com.ksa.unticovid.core.utils.UIError

sealed class ProfileEffects {
    data class ShowProfileError(val error: UIError) : ProfileEffects()
    data class ShowProfileSuccessfulAlert(@StringRes val message: Int) : ProfileEffects()
}
