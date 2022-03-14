package com.ksa.unticovid.modules.user_management.signin.presentation.view

import androidx.fragment.app.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.extentions.buildSignUpString
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.FragmentSignInBinding
import com.ksa.unticovid.modules.user_management.core.presentation.navigation.UserManagementNavigatorEvents
import com.ksa.unticovid.modules.user_management.signin.presentation.viewmodel.SignInViewModel
import javax.inject.Inject

class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {
    override val viewModel: SignInViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<UserManagementNavigatorEvents>

    override fun setup() {
        initViews()
    }

    private fun initViews() {

        binder.tvSignup.text =
            getString(R.string.newUserMessage).buildSignUpString(
                requireContext(),
                onSingUpClickListener = ::navigateToSignUpScreen
            )
    }

    private fun navigateToSignUpScreen() =
        navigator.onEvent(UserManagementNavigatorEvents.OpenSignUpScreen)
}
