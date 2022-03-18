package com.ksa.unticovid.modules.main.home.presentation.view

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.extentions.isNull
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.FragmentHomeBinding
import com.ksa.unticovid.modules.main.core.presentation.navigation.MainNavigatorEvents
import com.ksa.unticovid.modules.main.core.presentation.viewmodel.MainViewModel
import com.ksa.unticovid.modules.main.home.presentation.viewmodel.HomeViewModel
import com.ksa.unticovid.modules.result.domain.entity.param.ResultParams
import com.ksa.unticovid.modules.result.presentation.view.ResultDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by activityViewModels()
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<MainNavigatorEvents>

    private var resultDialog: ResultDialog? = null

    override fun setup() {
        initViews()
        initActions()
    }

    private fun initViews() {
        mainViewModel.showAppActionBar()
    }

    private fun initActions() {
        binder.itemInformation.setOnClickListener { showResultDialog() }
        binder.itemFaction.setOnClickListener { navigator.onEvent(MainNavigatorEvents.OpenAnalyticsScreen) }
        binder.itemTest.setOnClickListener { }
    }

    private fun showResultDialog() {
        resultDialog.isNull {
            resultDialog = ResultDialog.newInstance(
                ResultParams(
                    resultText = "hahahah",
                    isInfected = true,
                    doctorName = "Mahmoud Hassan",
                    doctorNumber = "0101010023345"
                )
            )
            resultDialog?.show(
                requireActivity().supportFragmentManager,
                ResultDialog::class.java.name
            )
            resultDialog?.onDismissListener = {
                resultDialog = null
            }
        }
    }

}