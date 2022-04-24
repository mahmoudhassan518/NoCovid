package com.ksa.unticovid.modules.main.home.presentation.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.FragmentHomeBinding
import com.ksa.unticovid.modules.main.core.presentation.navigation.MainNavigatorEvents
import com.ksa.unticovid.modules.main.core.presentation.viewmodel.MainViewModel
import com.ksa.unticovid.modules.main.home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by activityViewModels()
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<MainNavigatorEvents>


    override fun setup() {
        initViews()
        initActions()
    }

    private fun initViews() {
        mainViewModel.showAppActionBar()
    }

    private fun initActions() {
        binder.itemInformation.setOnClickListener { navigator.onEvent(MainNavigatorEvents.OpenInformationScreen) }
        binder.itemFaction.setOnClickListener { navigator.onEvent(MainNavigatorEvents.OpenFactionScreen) }
        binder.itemTest.setOnClickListener { navigator.onEvent(MainNavigatorEvents.OpenQuestionsScreen) }
    }


}