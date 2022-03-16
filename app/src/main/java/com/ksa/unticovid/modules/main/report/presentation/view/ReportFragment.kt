package com.ksa.unticovid.modules.main.report.presentation.view

import androidx.fragment.app.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.databinding.FragmentReportBinding
import com.ksa.unticovid.modules.main.report.presentation.viewmodel.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment :
    BaseFragment<FragmentReportBinding, ReportViewModel>(R.layout.fragment_report) {

    override val viewModel: ReportViewModel by viewModels()

    override fun setup() {
    }
}
