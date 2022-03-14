package com.ksa.unticovid.modules.user_management.signup.presentation.view

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.extentions.buildSignUpString
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.FragmentSignUpBinding
import com.ksa.unticovid.modules.user_management.core.presentation.navigation.UserManagementNavigatorEvents
import com.ksa.unticovid.modules.user_management.signup.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<FragmentSignUpBinding, SignUpViewModel>(R.layout.fragment_sign_up) {

    override val viewModel: SignUpViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<UserManagementNavigatorEvents>

    override fun setup() {
        initViews()
    }

    private fun initViews() {
        initSignUpSpannableText()
    }

    private fun initSignUpSpannableText() {
        val spannableString =
            getString(R.string.backToLoginMessage).buildSignUpString(
                requireContext(),
                getString(R.string.login),
                ::navigateToSignInScreen
            )

        binder.tvSignup.setText(spannableString, TextView.BufferType.SPANNABLE)
        binder.tvSignup.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun navigateToSignInScreen() =
        navigator.onEvent(UserManagementNavigatorEvents.OpenSignInScreen)

}
