package com.ksa.unticovid.modules.splash.presentaion.view

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.databinding.ActivitySplashBinding
import com.ksa.unticovid.modules.splash.presentaion.navigation.SplashNavigationCoordinator
import com.ksa.unticovid.modules.splash.presentaion.navigation.SplashNavigatorEvents
import com.ksa.unticovid.modules.splash.presentaion.viewmodel.SplashViewModel
import com.mahmoud.myframework.modules.splash.domain.IntroScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {

    override val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var navigator: SplashNavigationCoordinator

    override fun setup() {
        initObservation()
        viewModel.start()
    }

    private fun initObservation() {
        lifecycleScope.launchWhenCreated {
            viewModel.event.collectLatest {
                when (it) {
                    IntroScreen.HOME -> navigator.onEvent(SplashNavigatorEvents.OpenHomeScreen)
                    IntroScreen.USER_MANAGEMENT -> navigator.onEvent(SplashNavigatorEvents.OpenUserManagementScreen)
                }.also {
                    finish()
                }
            }
        }
    }
}
