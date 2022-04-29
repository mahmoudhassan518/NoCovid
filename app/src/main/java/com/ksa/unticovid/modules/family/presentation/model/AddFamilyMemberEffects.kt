package com.ksa.unticovid.modules.family.presentation.model

import androidx.annotation.StringRes

sealed class AddFamilyMemberEffects {
    data class ShowError(@StringRes val message: Int) : AddFamilyMemberEffects()
    data class ShowSuccessSubmitFamilyMembersMember(val member: FamilyMemberDataUIModel) : AddFamilyMemberEffects()
}
