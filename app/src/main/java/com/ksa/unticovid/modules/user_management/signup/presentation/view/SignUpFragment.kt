package com.ksa.unticovid.modules.user_management.signup.presentation.view

import androidx.fragment.app.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseFragment
import com.ksa.unticovid.databinding.FragmentSignUpBinding
import com.ksa.unticovid.modules.user_management.signup.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<FragmentSignUpBinding, SignUpViewModel>(R.layout.fragment_sign_up) {
    override val viewModel: SignUpViewModel by viewModels()

    override fun setup() {
    }
}
