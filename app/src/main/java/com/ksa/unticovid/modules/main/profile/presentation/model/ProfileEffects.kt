package com.ksa.unticovid.modules.main.profile.presentation.model

import androidx.annotation.StringRes
import com.ksa.unticovid.core.utils.UIError
import com.ksa.unticovid.modules.user_management.user.domain.entity.UserEntity

sealed class ProfileEffects {
    data class ShowResourceError(val error: UIError) : ProfileEffects()
    data class ShowToast(@StringRes val text: Int) : ProfileEffects()
    data class ShowRemoteError(val error: String) : ProfileEffects()
    data class DisplayUserData(val user: UserEntity) : ProfileEffects()
    data class ShowProfileSuccessfulAlert(val message: String) : ProfileEffects()
}
