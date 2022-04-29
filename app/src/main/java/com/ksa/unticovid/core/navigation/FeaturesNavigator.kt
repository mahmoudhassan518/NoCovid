package com.ksa.unticovid.core.navigation

interface FeaturesNavigator {
    fun openUserManagementScreen()
    fun openHomeScreen()
    fun openInformationScreen()
    fun openFamilyMembersScreen(id: String)
    fun openAddFamilyMemberScreen(id: String, withResult: Boolean)
    fun openFactionScreen()
    fun openQuestionsScreen()
    fun openReportDetailsScreen(id: String)
}
