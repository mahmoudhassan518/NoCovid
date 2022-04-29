package com.ksa.unticovid.modules.family.presentation.navigation

import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import javax.inject.Inject

class FamilyNavigationCoordinator @Inject constructor(
    private val featuresNavigator: FeaturesNavigator,
    private val familyFlowNavigator: FamilyFlowNavigator
) : NavigationCoordinator<FamilyNavigatorEvents> {

    override fun onStart(param: Any?) {
    }

    override fun onEvent(event: FamilyNavigatorEvents) {
        when (event) {
            is FamilyNavigatorEvents.OpenAddFamilyMemberScreen ->
                featuresNavigator.openAddFamilyMemberScreen(event.id, event.withResult)
            is FamilyNavigatorEvents.OpenFamilyMembersScreen ->
                familyFlowNavigator.openFamilyMembersScreen(event.member)
        }
    }
}
