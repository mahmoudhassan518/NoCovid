package com.ksa.unticovid.modules.user_management.core.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.ActivityUserManagementBinding
import com.ksa.unticovid.modules.user_management.core.presentation.navigation.UserManagementNavigatorEvents
import com.ksa.unticovid.modules.user_management.core.presentation.viewmodel.UserManagementViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserManagementActivity :
    BaseActivity<ActivityUserManagementBinding, UserManagementViewModel>(R.layout.activity_user_management) {

    override val viewModel: UserManagementViewModel
        by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<UserManagementNavigatorEvents>

    private val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_user_management_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun setup() {
        navigator.onStart(navController)
    }

    companion object {

        fun startActivity(activity: Activity) {
            val mainIntent = Intent(activity, UserManagementActivity::class.java)
            activity.startActivity(mainIntent)
            activity.finish()
        }
    }
}
