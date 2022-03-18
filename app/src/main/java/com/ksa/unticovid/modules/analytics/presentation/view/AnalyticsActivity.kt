package com.ksa.unticovid.modules.analytics.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.extentions.isNull
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.core.extentions.showAlerterSuccess
import com.ksa.unticovid.databinding.ActivityAnalyticsBinding
import com.ksa.unticovid.modules.analytics.presentation.adapter.AnalyticsAdapter
import com.ksa.unticovid.modules.analytics.presentation.dialog.UploadDialog
import com.ksa.unticovid.modules.analytics.presentation.model.AnalyticsEffects
import com.ksa.unticovid.modules.analytics.presentation.model.AnalyticsUIModel
import com.ksa.unticovid.modules.analytics.presentation.viewmodel.AnalyticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class AnalyticsActivity :
    BaseActivity<ActivityAnalyticsBinding, AnalyticsViewModel>(R.layout.activity_analytics) {

    override val viewModel: AnalyticsViewModel by viewModels()

    private var uploadDialog: UploadDialog? = null

    @Inject
    lateinit var analyticsAdapter: AnalyticsAdapter

    override fun setup() {
        loadAnalytics()
        initViews()
        initActions()
        initObservations()

    }

    private fun initViews() {
        initRecyclerViewAdapter()
        initSwapRefresher()
    }

    private fun initSwapRefresher() {
        binder.swContainer.setOnRefreshListener {
            binder.swContainer.isRefreshing = false
            loadAnalytics()
        }
    }

    private fun initRecyclerViewAdapter() {
        with(binder.rvAnalytics) {

            layoutManager = LinearLayoutManager(context)

            val itemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.divider
                )!!
            )
            addItemDecoration(itemDecoration)

            adapter = analyticsAdapter
        }
    }

    private fun initActions() {
        binder.layoutStateView.tvRetry.setOnClickListener { loadAnalytics() }
        binder.btnUploadImage.setOnClickListener { showUploadDialog() }

    }

    private fun initObservations() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                renderUiModel(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.uiEffects.collectLatest {
                renderEffects(it)
            }
        }
    }

    private fun renderEffects(it: AnalyticsEffects) {
        when (it) {
            is AnalyticsEffects.ShowAnalyticsError -> showAlerterError(getString(it.error))
            is AnalyticsEffects.ShowAnalyticsSuccessfulAlert -> showAlerterSuccess(getString(it.message))
        }
    }

    private fun renderUiModel(uiModel: AnalyticsUIModel) {
        renderStateView(uiModel)
        analyticsAdapter.submitList(uiModel.analytics)
    }

    private fun renderStateView(uiModel: AnalyticsUIModel) {
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

    private fun loadAnalytics() = viewModel.getAnalytics()

    private fun showUploadDialog() {
        uploadDialog.isNull {
            uploadDialog = UploadDialog.newInstance(

            )
            uploadDialog?.show(
                supportFragmentManager,
                UploadDialog::class.java.name
            )
            uploadDialog?.onDismissListener = {
                uploadDialog = null
            }
        }
    }

    companion object {

        fun startActivity(activity: Activity) {
            val mainIntent = Intent(activity, AnalyticsActivity::class.java)
            activity.startActivity(mainIntent)
        }
    }

}