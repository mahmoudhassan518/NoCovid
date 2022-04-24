package com.ksa.unticovid.modules.user_management.signup.presentation.view

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.core.extentions.buildSignUpString
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.FragmentSignUpBinding
import com.ksa.unticovid.modules.user_management.core.presentation.model.GenderSettings
import com.ksa.unticovid.modules.user_management.core.presentation.navigation.UserManagementNavigatorEvents
import com.ksa.unticovid.modules.user_management.signin.presentation.model.SignInEffects
import com.ksa.unticovid.modules.user_management.signin.presentation.model.SignInUIModel
import com.ksa.unticovid.modules.user_management.signup.domain.entity.param.SignUpParam
import com.ksa.unticovid.modules.user_management.signup.presentation.model.SignUpEffects
import com.ksa.unticovid.modules.user_management.signup.presentation.model.SignUpUIModel
import com.ksa.unticovid.modules.user_management.signup.presentation.viewmodel.SignUpViewModel
import com.ksa.unticovid.modules.user_management.user.domain.entity.GenderType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<FragmentSignUpBinding, SignUpViewModel>(R.layout.fragment_sign_up) {

    override val viewModel: SignUpViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<UserManagementNavigatorEvents>

    override fun setup() {
        initViews()
        initActions()
        initObservations()
    }

    private fun initViews() {
        initSignUpSpannableText()
    }

    private fun initActions() {
        binder.btnSignup.setOnClickListener {
            viewModel.signUp(
                binder.buildSignUpParam()
            )
        }
        binder.layoutGenderView.tvMale.setOnClickListener {
            viewModel.updateCurrentGender(GenderType.MALE)
        }
        binder.layoutGenderView.tvFemale.setOnClickListener {
            viewModel.updateCurrentGender(GenderType.FEMALE)
        }
    }

    private fun FragmentSignUpBinding.buildSignUpParam() = with(this) {
        SignUpParam(
            identity = etNationalId.text.toString(),
            email = etEmail.text.toString(),
            password = etCurrentPassword.text.toString(),
            confirmPassword = etConfirmPassword.text.toString(),
            gender = GenderType.MALE.name,
            age = etAge.text.toString(),
            address = etAddress.text.toString(),
            mobile = etPhone.text.toString(),
            name = etName.text.toString(),
        )
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

    private fun renderUIModel(it: SignUpUIModel) = with(it) {
        binder.layoutStateView.root.isVisible = isLoading
        binder.layoutStateView.cvLoading.isVisible = isLoading

        genderSettings.renderGenderSettings()
    }

    private fun GenderSettings.renderGenderSettings() = with(binder) {
        layoutGenderView.tvMale.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                maleBackground
            )
        )
        layoutGenderView.tvMale.setTextColor(ContextCompat.getColor(requireActivity(), maleColor))

        layoutGenderView.tvFemale.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                femaleBackground
            )
        )
        layoutGenderView.tvFemale.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                femaleColor
            )
        )
    }


    private fun initEffectObserver() =
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEffects.collectLatest {
                renderEffects(it)
            }
        }

    private fun renderEffects(it: SignUpEffects) {
        when (it) {
            is SignUpEffects.ShowRemoteError -> requireActivity().showAlerterError(it.error)
            is SignUpEffects.ShowResourceError -> requireActivity().showAlerterError(getString(it.error.msg))
            is SignUpEffects.NavigateToMainScreen -> navigateToMainScreen()
        }
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

    private fun navigateToMainScreen() =
        navigator.onEvent(UserManagementNavigatorEvents.OpenHomeScreen)
}