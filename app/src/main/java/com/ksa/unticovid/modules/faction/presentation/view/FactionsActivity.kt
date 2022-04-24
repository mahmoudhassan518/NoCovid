package com.ksa.unticovid.modules.faction.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.extentions.setupWebView
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.databinding.ActivityFactionsBinding
import com.ksa.unticovid.modules.faction.presentation.model.FactionsEffects
import com.ksa.unticovid.modules.faction.presentation.model.FactionsUIModel
import com.ksa.unticovid.modules.faction.presentation.viewmodel.FactionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FactionsActivity :
    BaseActivity<ActivityFactionsBinding, FactionsViewModel>(R.layout.activity_factions) {
    override val viewModel: FactionsViewModel by viewModels()

    override fun setup() {
        initViews()
        initObservations()
        initActions()
        displayFactions()
    }

    private fun initViews() {
        initWebView()
    }

    private fun initWebView() = with(binder.webView) { setupWebView() }

    private fun initActions() {
        binder.layoutStateView.tvRetry.setOnClickListener {
            displayFactions()
        }
        binder.actionBar.backImageButton.setOnClickListener { finish() }
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

    private fun renderEffects(it: FactionsEffects) {
        when (it) {
            is FactionsEffects.ShowFactionsError -> showAlerterError(
                getString(it.message)
            )
        }
    }

    private fun renderUiModel(uiModel: FactionsUIModel) {
        renderStateView(uiModel)
    }

    private fun renderStateView(uiModel: FactionsUIModel) {
        binder.layoutStateView.root.isVisible = uiModel.isLoading.or(uiModel.errorMessage != null)
        binder.layoutStateView.cvLoading.isVisible = uiModel.isLoading
        binder.layoutStateView.clError.isVisible = (uiModel.errorMessage != null)

        uiModel.errorMessage?.let {
            binder.layoutStateView.tvErrorMessage.text = getString(it)
        }

        uiModel.factions?.let {
            binder.webView.loadDataWithBaseURL(null, it.data, "text/html", "UTF-8", null)

        }
    }

    private fun displayFactions() = viewModel.getUserFactions()

    companion object {

        fun startActivity(activity: Activity) {
            val mainIntent = Intent(activity, FactionsActivity::class.java)
            activity.startActivity(mainIntent)
        }
    }
}
