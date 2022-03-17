package com.ksa.unticovid.modules.main.report.presentation.view

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.databinding.FragmentReportBinding
import com.ksa.unticovid.modules.main.report.presentation.adapter.ReportAdapter
import com.ksa.unticovid.modules.main.report.presentation.model.ReportListEffects
import com.ksa.unticovid.modules.main.report.presentation.model.ReportUIModel
import com.ksa.unticovid.modules.main.report.presentation.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ReportFragment :
    BaseFragment<FragmentReportBinding, ReportViewModel>(R.layout.fragment_report) {

    override val viewModel: ReportViewModel by viewModels()

    @Inject
    lateinit var reportAdapter: ReportAdapter

    override fun setup() {
        initRecyclerViewAdapter()
        initObservations()
        initActions()
        viewModel.getUserReports()
    }

    private fun initActions() {
        binder.layoutError.tvRetry.setOnClickListener { viewModel.getUserReports() }
    }

    private fun initObservations() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                renderUiModel(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEffects.collectLatest {
                renderEffects(it)
            }
        }
    }

    private fun renderEffects(it: ReportListEffects) {
        when (it) {
            is ReportListEffects.ShowReportListError -> requireActivity().showAlerterError(
                requireActivity().getString(it.message)
            )
        }
    }

    private fun renderUiModel(uiModel: ReportUIModel) {
        binder.layoutLoading.visibility = if (uiModel.isLoading) View.VISIBLE else View.GONE
        binder.layoutError.root.visibility =
            if (uiModel.errorMessage != null) View.VISIBLE else View.GONE

        uiModel.errorMessage?.let {
            binder.layoutError.tvErrorMessage.text = getString(it)
        }
        reportAdapter.submitList(uiModel.reports)
    }

    private fun initRecyclerViewAdapter() {
        with(binder.rvAnalytics) {

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
}
