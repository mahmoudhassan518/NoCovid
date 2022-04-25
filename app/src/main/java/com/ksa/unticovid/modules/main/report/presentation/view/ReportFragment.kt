package com.ksa.unticovid.modules.main.report.presentation.view

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.FragmentReportBinding
import com.ksa.unticovid.modules.main.core.presentation.navigation.MainNavigatorEvents
import com.ksa.unticovid.modules.main.core.presentation.viewmodel.MainViewModel
import com.ksa.unticovid.modules.main.report.presentation.adapter.ReportAdapter
import com.ksa.unticovid.modules.main.report.presentation.model.ReportEffects
import com.ksa.unticovid.modules.main.report.presentation.model.ReportUIModel
import com.ksa.unticovid.modules.main.report.presentation.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ReportFragment :
    BaseFragment<FragmentReportBinding, ReportViewModel>(R.layout.fragment_report) {

    private val mainViewModel: MainViewModel by activityViewModels()
    override val viewModel: ReportViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<MainNavigatorEvents>

    @Inject
    lateinit var reportAdapter: ReportAdapter

    override fun setup() {
        initViews()
        initObservations()
        initActions()
        loadReports()
    }

    private fun initViews() {
        initRecyclerViewAdapter()
        initSwapRefresher()
        mainViewModel.showAppActionBar()
    }

    private fun initRecyclerViewAdapter() {
        with(binder.rvReports) {

            layoutManager = LinearLayoutManager(requireContext())

            val itemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.divider
                )!!
            )
            addItemDecoration(itemDecoration)

            adapter = reportAdapter
        }
    }

    private fun initSwapRefresher() {
        binder.swContainer.setOnRefreshListener {
            binder.swContainer.isRefreshing = false
            loadReports()
        }
    }

    private fun initActions() {
        binder.layoutStateView.tvRetry.setOnClickListener { loadReports() }
        reportAdapter.itemClickListener = {
            navigator.onEvent(MainNavigatorEvents.OpenReportDetailsScreen(it.id))
        }
    }

    private fun initObservations() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                renderUIMode(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEffects.collectLatest {
                renderEffects(it)
            }
        }
    }

    private fun renderEffects(it: ReportEffects) {
        when (it) {
            is ReportEffects.ShowReportError -> requireActivity().showAlerterError(
                requireActivity().getString(it.message)
            )
        }
    }

    private fun renderUIMode(uiModel: ReportUIModel) {
        renderStateView(uiModel)
        reportAdapter.submitList(uiModel.reports)
    }

    private fun renderStateView(uiModel: ReportUIModel) {
        binder.layoutStateView.root.isVisible =
            uiModel.isLoading.or(uiModel.errorMessage != null).or(uiModel.emptyMessage != null)
        binder.layoutStateView.cvLoading.isVisible = uiModel.isLoading
        binder.layoutStateView.clError.isVisible = (uiModel.errorMessage != null)
        binder.layoutStateView.clEmpty.isVisible = (uiModel.emptyMessage != null)

        uiModel.errorMessage?.let {
            binder.layoutStateView.tvErrorMessage.text = getString(it)
        }
        uiModel.emptyMessage?.let {
            binder.layoutStateView.tvEmptyMessage.text = getString(it)
        }
    }

    private fun loadReports() = viewModel.getUserReports()
}
