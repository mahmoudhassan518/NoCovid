package com.ksa.unticovid.modules.faction.presentation.view

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
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
        initObservations()
        initActions()
        displayFactions()
    }

    private fun initActions() {
        binder.layoutError.tvRetry.setOnClickListener {
            displayFactions()
        }
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
        binder.layoutLoading.visibility = if (uiModel.isLoading) View.VISIBLE else View.GONE
        binder.layoutError.root.visibility =
            if (uiModel.errorMessage != null) View.VISIBLE else View.GONE

        uiModel.errorMessage?.let {
            binder.layoutError.tvErrorMessage.text = getString(it)
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
