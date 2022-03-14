package com.ksa.unticovid.modules.common.presentation.navigation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.modules.home.core.presentation.view.HomeActivity
import com.ksa.unticovid.modules.user_management.core.presentation.view.UserManagementActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class FeaturesNavigatorImpl @Inject constructor(@ActivityContext private val context: Context) :
    FeaturesNavigator {
    override fun openUserManagementScreen() =
        UserManagementActivity.startActivity(context as AppCompatActivity)

    override fun openHomeScreen() =
        HomeActivity.startActivity(context as AppCompatActivity)
}
