package com.ksa.unticovid.modules.user_management.signin.presentation.view

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.extentions.buildSignUpString
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.FragmentSignInBinding
import com.ksa.unticovid.modules.user_management.core.presentation.navigation.UserManagementNavigatorEvents
import com.ksa.unticovid.modules.user_management.signin.domain.entity.param.SignInParam
import com.ksa.unticovid.modules.user_management.signin.presentation.model.SignInEffects
import com.ksa.unticovid.modules.user_management.signin.presentation.model.SignInUIModel
import com.ksa.unticovid.modules.user_management.signin.presentation.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {
    override val viewModel: SignInViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<UserManagementNavigatorEvents>

    override fun setup() {
        initViews()
        initActions()
        initObservations()
    }

    private fun initActions() {
        binder.btnLogin.setOnClickListener {
            viewModel.signIn(
                SignInParam(
                    identity = binder.etNationalId.text.toString(),
                    password = binder.etCurrentPassword.text.toString()
                )
            )
        }
    }

    private fun initViews() {
        initSignUpSpannableText()
    }

    private fun initObservations() {
        initUIObserver()
        initEffectObserver()

    }

    private fun initUIObserver() =
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                renderUIModel(it)
            }
        }

    private fun renderUIModel(it: SignInUIModel) = with(it) {
        binder.layoutStateView.root.isVisible = isLoading
        binder.layoutStateView.cvLoading.isVisible = isLoading
    }

    private fun initEffectObserver() =
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEffects.collectLatest {
                renderEffects(it)
            }
        }
    private fun renderEffects(it: SignInEffects) {
        when (it) {
            is SignInEffects.ShowRemoteError -> requireActivity().showAlerterError(it.error)
            is SignInEffects.ShowResourceError -> requireActivity().showAlerterError(getString(it.error.msg))
            is SignInEffects.NavigateToMainScreen -> navigateToMainScreen()
        }
    }

    private fun initSignUpSpannableText() {
        val spannableString =
            getString(R.string.newUserMessage).buildSignUpString(
                requireContext(),
                getString(R.string.registerNow),
                ::navigateToSignUpScreen
            )

        binder.tvSignup.setText(spannableString, TextView.BufferType.SPANNABLE)
        binder.tvSignup.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun navigateToSignUpScreen() =
        navigator.onEvent(UserManagementNavigatorEvents.OpenSignUpScreen)

    private fun navigateToMainScreen() =
        navigator.onEvent(UserManagementNavigatorEvents.OpenHomeScreen)
}
