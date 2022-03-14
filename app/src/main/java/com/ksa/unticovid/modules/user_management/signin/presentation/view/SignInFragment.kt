package com.ksa.unticovid.modules.user_management.signin.presentation.view

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.extentions.buildSignUpString
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.FragmentSignInBinding
import com.ksa.unticovid.modules.user_management.core.presentation.navigation.UserManagementNavigatorEvents
import com.ksa.unticovid.modules.user_management.signin.presentation.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
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
    }

    private fun initActions() {
        binder.btnLogin.setOnClickListener {
            requireActivity().showAlerterError("erorororororor")
        }
    }

    private fun initViews() {
        initSignUpSpannableText()
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
}
