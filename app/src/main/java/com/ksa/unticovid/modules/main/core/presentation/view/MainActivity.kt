package com.ksa.unticovid.modules.main.core.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.extentions.setAppTitleSpanStyle
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.ActivityMainBinding
import com.ksa.unticovid.modules.main.core.presentation.model.MainUIModel
import com.ksa.unticovid.modules.main.core.presentation.navigation.MainNavigatorEvents
import com.ksa.unticovid.modules.main.core.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<MainNavigatorEvents>

    private val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun setup() {
        initToolBar()
        initObservation()
    }

    private fun initObservation() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { renderUIModel(it) }
        }
    }

    private fun renderUIModel(uiModel: MainUIModel) {
        binder.clHeader.isVisible = uiModel.showAppActionBar
    }

    private fun initToolBar() {
        binder.tvAppName.setAppTitleSpanStyle(this)
        NavigationUI.setupWithNavController(binder.bottomNavView, navController)

    }

    companion object {

        fun startActivity(activity: Activity) {
            val mainIntent = Intent(activity, MainActivity::class.java)
            activity.startActivity(mainIntent)
            activity.finish()
        }
    }
}
