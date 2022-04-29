package com.ksa.unticovid.modules.family.presentation.navigation

import com.ksa.unticovid.modules.family.presentation.model.FamilyMemberDataUIModel

sealed class FamilyNavigatorEvents {

    data class OpenAddFamilyMemberScreen(val id: String, val withResult: Boolean) :
        FamilyNavigatorEvents()

    data class OpenFamilyMembersScreen(val member: FamilyMemberDataUIModel) :
        FamilyNavigatorEvents()
}
