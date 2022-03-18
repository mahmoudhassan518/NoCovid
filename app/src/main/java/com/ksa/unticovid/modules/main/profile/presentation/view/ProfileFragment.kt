package com.ksa.unticovid.modules.main.profile.presentation.view

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.core.extentions.showAlerterSuccess
import com.ksa.unticovid.databinding.FragmentProfileBinding
import com.ksa.unticovid.modules.main.core.presentation.viewmodel.MainViewModel
import com.ksa.unticovid.modules.main.profile.presentation.model.ProfileEffects
import com.ksa.unticovid.modules.main.profile.presentation.model.ProfileUIModel
import com.ksa.unticovid.modules.main.profile.presentation.viewmodel.ProfileViewModel
import com.ksa.unticovid.modules.user.domain.entity.param.UserParam
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    private val mainViewModel: MainViewModel by activityViewModels()
    override val viewModel: ProfileViewModel by viewModels()

    override fun setup() {
        viewModel.getRemoteUserData()
        initViews()
        initObservations()
        initActions()
        binder.srRefresher.setOnRefreshListener {
            binder.srRefresher.isRefreshing = false
            viewModel.getRemoteUserData()
        }
    }

    private fun initViews() {
        mainViewModel.showAppActionBar(false)
    }

    private fun initActions() {
        binder.viewProfileInputs.btnSave.setOnClickListener {
            viewModel.saveProfileData(
                UserParam(
                    name = binder.viewProfileInputs.etName.text.toString(),
                    nationalId = binder.viewProfileInputs.etNationalId.text.toString(),
                    phoneNumber = binder.viewProfileInputs.etPhone.text.toString()
                )
            )
        }
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

    private fun renderUiModel(uiModel: ProfileUIModel) {
        renderStateView(uiModel)

        uiModel.user?.let { user ->
            binder.viewProfileInputs.tvWelcomeMessage.text =
                getString(R.string.profileWelcomeMessage, user.name)
            binder.viewProfileInputs.etName.setText(user.name)
            binder.viewProfileInputs.etNationalId.setText(user.nationalId)
            binder.viewProfileInputs.etPhone.setText(user.phoneNumber)
        }
    }

    private fun renderStateView(uiModel: ProfileUIModel) {
        binder.layoutStateView.root.isVisible = uiModel.isLoading
        binder.layoutStateView.cvLoading.isVisible = uiModel.isLoading
    }

    private fun renderEffects(it: ProfileEffects) {
        when (it) {
            is ProfileEffects.ShowProfileError -> requireActivity().showAlerterError(
                errorMessage = requireActivity().getString(
                    it.error.msg
                ),
                errorTitle = it.error.title?.let { it1 ->
                    requireActivity().getString(
                        it1
                    )
                }
            )
            is ProfileEffects.ShowProfileSuccessfulAlert -> requireActivity().showAlerterSuccess(
                requireActivity().getString(
                    it.message
                )
            )
        }
    }
}
