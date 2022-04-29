package com.ksa.unticovid.modules.family.presentation.model

import androidx.annotation.StringRes

sealed class FamilyMembersEffects {
    data class ShowError(@StringRes val message: Int) : FamilyMembersEffects()
}
