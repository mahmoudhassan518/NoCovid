package com.ksa.unticovid.modules.family.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.ActivityAddFamilyMemberBinding
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam
import com.ksa.unticovid.modules.family.presentation.model.AddFamilyMemberEffects
import com.ksa.unticovid.modules.family.presentation.model.AddFamilyMemberUIModel
import com.ksa.unticovid.modules.family.presentation.model.FamilyMemberDataUIModel
import com.ksa.unticovid.modules.family.presentation.navigation.FamilyNavigatorEvents
import com.ksa.unticovid.modules.family.presentation.viewmodel.AddFamilyMemberViewModel
import com.ksa.unticovid.modules.user_management.core.presentation.model.GenderSettings
import com.ksa.unticovid.modules.user_management.user.domain.entity.GenderType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class AddFamilyMemberActivity :
    BaseActivity<ActivityAddFamilyMemberBinding, AddFamilyMemberViewModel>(R.layout.activity_add_family_member) {

    override val viewModel: AddFamilyMemberViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<FamilyNavigatorEvents>


    private val reportId: String by lazy {
        intent.getStringExtra(REPORT_ID)!!
    }

    override fun setup() {
        initObservations()
        initActions()
    }


    private fun initActions() {
        binder.actionBar.backImageButton.setOnClickListener { onBackPressed() }
        binder.btnSubmit.setOnClickListener {
            addFamilyMember(binder.buildAddFamilyMemberParam())
        }
        binder.layoutGenderView.tvLeft.setOnClickListener {
            viewModel.updateCurrentGender(GenderType.MALE)
        }
        binder.layoutGenderView.tvRight.setOnClickListener {
            viewModel.updateCurrentGender(GenderType.FEMALE)
        }
    }

    private fun ActivityAddFamilyMemberBinding.buildAddFamilyMemberParam() = with(this) {
        AddFamilyMemberParam(
            name = etName.text.toString(),
            identity = etNationalId.text.toString(),
            mobile = etPhone.text.toString(),
            age = etAge.text.toString(),
            relation = etRelation.text.toString(),
            gender = "",
            reportId = reportId
        )
    }

    private fun initObservations() {

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest {
                renderUIMode(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.uiEffects.collectLatest {
                renderEffects(it)
            }
        }
    }

    private fun renderEffects(it: AddFamilyMemberEffects) {
        when (it) {
            is AddFamilyMemberEffects.ShowError -> showAlerterError(getString(it.message))
            is AddFamilyMemberEffects.ShowSuccessSubmitFamilyMembersMember -> onFamilyMemberAddedSuccessfully(
                it.member
            )
        }
    }

    private fun onFamilyMemberAddedSuccessfully(it: FamilyMemberDataUIModel) {
        navigator.onEvent(FamilyNavigatorEvents.OpenFamilyMembersScreen(it))
    }

    private fun renderUIMode(uiModel: AddFamilyMemberUIModel) {
        uiModel.renderStateView()
        uiModel.genderSettings.renderGenderSettings()
    }

    private fun GenderSettings.renderGenderSettings() = with(binder) {
        layoutGenderView.tvLeft.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this@AddFamilyMemberActivity,
                maleBackground
            )
        )
        layoutGenderView.tvLeft.setTextColor(
            ContextCompat.getColor(
                this@AddFamilyMemberActivity,
                maleColor
            )
        )

        layoutGenderView.tvRight.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this@AddFamilyMemberActivity,
                femaleBackground
            )
        )
        layoutGenderView.tvRight.setTextColor(
            ContextCompat.getColor(
                this@AddFamilyMemberActivity,
                femaleColor
            )
        )
    }


    private fun AddFamilyMemberUIModel.renderStateView() = with(this) {
        binder.layoutStateView.root.isVisible = isLoading
        binder.layoutStateView.cvLoading.isVisible = isLoading
    }

    private fun addFamilyMember(param: AddFamilyMemberParam) = viewModel.addFamilyMember(param)


    companion object {
        private const val REPORT_ID = "REPORT_ID"
        const val NEW_MEMBER_CODE = 803
        fun startActivity(activity: Activity, id: String) {
            activity.startActivity(
                Intent(activity, AddFamilyMemberActivity::class.java)
                    .apply {
                        putExtra(REPORT_ID, id)
                    }
            )
        }

        fun startActivityForResult(activity: Activity, id: String) {
            activity.startActivityForResult(
                Intent(activity, AddFamilyMemberActivity::class.java)
                    .apply {
                        putExtra(REPORT_ID, id)
                    }, NEW_MEMBER_CODE
            )
        }

    }
}