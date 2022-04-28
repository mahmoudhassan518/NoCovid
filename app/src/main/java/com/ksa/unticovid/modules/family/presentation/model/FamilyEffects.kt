package com.ksa.unticovid.modules.family.presentation.model

import androidx.annotation.StringRes

sealed class FamilyEffects {
    data class ShowError(@StringRes val message: Int) : FamilyEffects()
    data class ShowSuccessSubmitFamilyMember(@StringRes val message: Int) : FamilyEffects()
}
