package com.ksa.unticovid.modules.information.presentation.view

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.databinding.ActivityInformationBinding
import com.ksa.unticovid.modules.information.presentation.model.InformationEffects
import com.ksa.unticovid.modules.information.presentation.model.InformationUIModel
import com.ksa.unticovid.modules.information.presentation.viewmodel.InformationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class InformationActivity :
    BaseActivity<ActivityInformationBinding, InformationViewModel>(R.layout.activity_information) {
    override val viewModel: InformationViewModel by viewModels()

    override fun setup() {
        initObservations()
        initActions()

        displayCovidInformation()
    }

    private fun initActions() {
        binder.layoutError.tvRetry.setOnClickListener {
            displayCovidInformation()
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

    private fun renderEffects(it: InformationEffects) {
        when (it) {
            is InformationEffects.ShowInformationError -> showAlerterError(
                getString(it.message)
            )
        }
    }

    private fun renderUiModel(uiModel: InformationUIModel) {
        binder.layoutLoading.visibility = if (uiModel.isLoading) View.VISIBLE else View.GONE
        binder.layoutError.root.visibility =
            if (uiModel.errorMessage != null) View.VISIBLE else View.GONE

        uiModel.errorMessage?.let {
            binder.layoutError.tvErrorMessage.text = getString(it)
        }
    }

    private fun displayCovidInformation() = viewModel.getCovidInformation()

    companion object {

        fun startActivity(activity: Activity) {
            val mainIntent = Intent(activity, InformationActivity::class.java)
            activity.startActivity(mainIntent)
        }
    }
}
