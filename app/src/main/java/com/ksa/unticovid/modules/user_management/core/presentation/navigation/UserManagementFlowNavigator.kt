package com.ksa.unticovid.modules.user_management.core.presentation.navigation

import androidx.navigation.NavController
import com.ksa.unticovid.R
import javax.inject.Inject

class UserManagementFlowNavigator @Inject constructor() {

    lateinit var navController: NavController

    fun showSignUpFragment() =
        navController.navigate(R.id.action_signInFragment_to_signUpFragment)

    fun showSignInFragment() =
        navController.popBackStack()
}
