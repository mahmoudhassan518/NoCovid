package com.ksa.unticovid.modules.main.report.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.extentions.isNull
import com.ksa.unticovid.core.extentions.loadImage
import com.ksa.unticovid.databinding.ActivityReportDetailsBinding
import com.ksa.unticovid.modules.main.report.presentation.adapter.PCRAdapter
import com.ksa.unticovid.modules.main.report.presentation.model.ReportDetailsUIModel
import com.ksa.unticovid.modules.main.report.presentation.viewmodel.ReportDetailsViewModel
import com.ksa.unticovid.modules.upload.presentation.dialog.UploadDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ReportDetailsActivity :
    BaseActivity<ActivityReportDetailsBinding, ReportDetailsViewModel>(R.layout.activity_report_details) {

    override val viewModel: ReportDetailsViewModel by viewModels()

    @Inject
    lateinit var pcrAdapter: PCRAdapter

    private var uploadDialog: UploadDialog? = null

    private val reportId: String by lazy {
        intent.getStringExtra(
            REPORT_ID
        )!!
    }

    override fun setup() {
        initViews()
        initActions()
        initObservations()
        getReportDetails()

    }

    private fun initViews() {
        initRecyclerViewAdapter()
    }

    private fun initActions() {
        binder.layoutStateView.tvRetry.setOnClickListener { getReportDetails() }
        binder.btnUploadImage.setOnClickListener { showUploadDialog() }
        binder.actionBar.backImageButton.setOnClickListener { finish() }
        pcrAdapter.itemClickListener = {
            viewModel.updateCurrentReportPCR(it)
        }
    }

    private fun initObservations() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                renderUIModel(it)
            }
        }
    }

    private fun renderUIModel(uiModel: ReportDetailsUIModel) {
        renderStateView(uiModel)

        pcrAdapter.submitList(uiModel.report?.covidData?.pcrList)
        binder.ivImage.isVisible = uiModel.report != null
        uiModel.currentReportPCR?.let { binder.ivImage.loadImage(it.image) }
    }

    private fun renderStateView(uiModel: ReportDetailsUIModel) {
        binder.layoutStateView.root.isVisible =
            uiModel.isLoading.or(uiModel.errorMessage != null)
        binder.layoutStateView.cvLoading.isVisible = uiModel.isLoading
        binder.layoutStateView.clError.isVisible = (uiModel.errorMessage != null)

        uiModel.errorMessage?.let {
            binder.layoutStateView.tvErrorMessage.text = getString(it)
        }

        binder.layoutStateView.root.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                if (uiModel.report == null) R.color.white
                else R.color.colorTransparent
            )
        )

    }

    private fun getReportDetails() = viewModel.getReportDetails(reportId)

    private fun showUploadDialog() {
        uploadDialog.isNull {
            uploadDialog = UploadDialog.newInstance(reportId)
            uploadDialog?.show(
                supportFragmentManager,
                UploadDialog::class.java.name
            )
            uploadDialog?.onDismissListener = {
                uploadDialog = null
            }
            uploadDialog?.onImageUploadedListener = {
                getReportDetails()
            }
        }
    }

    private fun initRecyclerViewAdapter() {
        with(binder.rvImages) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = pcrAdapter
        }
    }

    companion object {
        private const val REPORT_ID = "REPORT_ID"
        fun startActivity(activity: Activity, id: String) {
            activity.startActivity(
                Intent(activity, ReportDetailsActivity::class.java)
                    .apply {
                        putExtra(REPORT_ID, id)
                    }
            )
        }
    }


}