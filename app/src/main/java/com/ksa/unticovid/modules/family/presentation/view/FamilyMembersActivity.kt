package com.ksa.unticovid.modules.family.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.extentions.showAlerterError
import com.ksa.unticovid.core.extentions.showAlerterSuccess
import com.ksa.unticovid.core.navigation.NavigationCoordinator
import com.ksa.unticovid.databinding.ActivityFamilyMembersBinding
import com.ksa.unticovid.modules.family.presentation.adapter.FamilyAdapter
import com.ksa.unticovid.modules.family.presentation.model.FamilyMemberDataUIModel
import com.ksa.unticovid.modules.family.presentation.model.FamilyMembersEffects
import com.ksa.unticovid.modules.family.presentation.model.FamilyMembersUIModel
import com.ksa.unticovid.modules.family.presentation.navigation.FamilyNavigatorEvents
import com.ksa.unticovid.modules.family.presentation.view.AddFamilyMemberActivity.Companion.NEW_MEMBER_CODE
import com.ksa.unticovid.modules.family.presentation.viewmodel.FamilyMembersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class FamilyMembersActivity :
    BaseActivity<ActivityFamilyMembersBinding, FamilyMembersViewModel>(R.layout.activity_family_members) {

    override val viewModel: FamilyMembersViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigationCoordinator<FamilyNavigatorEvents>

    @Inject
    lateinit var familyAdapter: FamilyAdapter

    private val reportId: String by lazy {
        intent.getStringExtra(REPORT_ID)!!
    }

    override fun setup() {
        initViews()
        initObservations()
        initActions()
        loadFamily()
    }

    private fun initViews() {
        initRecyclerViewAdapter()
        initSwapRefresher()
    }

    private fun initRecyclerViewAdapter() {
        with(binder.rvFamily) {
            layoutManager = LinearLayoutManager(context)
            adapter = familyAdapter
        }
    }

    private fun initSwapRefresher() {
        binder.swContainer.setOnRefreshListener {
            binder.swContainer.isRefreshing = false
            loadFamily()
        }
    }

    private fun initActions() {
        binder.layoutStateView.tvRetry.setOnClickListener { loadFamily() }
        binder.actionBar.backImageButton.setOnClickListener { onBackPressed() }
        binder.btnAddMember.setOnClickListener { openAddFamilyMemberScreen() }
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

    private fun renderEffects(it: FamilyMembersEffects) {
        when (it) {
            is FamilyMembersEffects.ShowError -> showAlerterError(getString(it.message))
        }
    }

    private fun renderUIMode(uiModel: FamilyMembersUIModel) {
        renderStateView(uiModel)
        familyAdapter.submitData(uiModel.family)
    }

    private fun renderStateView(uiModel: FamilyMembersUIModel) {
        binder.layoutStateView.root.isVisible =
            uiModel.isLoading.or(uiModel.errorMessage != null).or(uiModel.emptyMessage != null)
        binder.layoutStateView.cvLoading.isVisible = uiModel.isLoading
        binder.layoutStateView.clError.isVisible = (uiModel.errorMessage != null)
        binder.layoutStateView.clEmpty.isVisible = (uiModel.emptyMessage != null)

        uiModel.errorMessage?.let {
            binder.layoutStateView.tvErrorMessage.text = getString(it)
        }
        uiModel.emptyMessage?.let {
            binder.layoutStateView.tvEmptyMessage.text = getString(it)
        }

        binder.layoutStateView.root.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                if (uiModel.family.isNullOrEmpty()) R.color.white
                else R.color.colorTransparent
            )
        )
    }

    private fun loadFamily() = viewModel.getFamilyMembers(reportId)

    private fun openAddFamilyMemberScreen() =
        navigator.onEvent(FamilyNavigatorEvents.OpenAddFamilyMemberScreen(reportId, true))

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_MEMBER_CODE && resultCode == RESULT_OK && data != null) {
            val member: FamilyMemberDataUIModel? = data.getParcelableExtra(NEW_MEMBER_DATA)
            onPersonAddedSuccessfully(member)
        }
    }

    private fun onPersonAddedSuccessfully(member: FamilyMemberDataUIModel?) {
        member?.let {
            viewModel.addFamilyMemberToList(it)
            showAlerterSuccess(
                getString(
                    R.string.msgMemberFamilyAddedSuccessfully,
                    member.name
                )
            )
        } ?: run {
            loadFamily()
        }
    }


    companion object {
        private const val REPORT_ID = "REPORT_ID"
        const val NEW_MEMBER_DATA = "NEW_MEMBER_DATA"


        fun startActivity(activity: Activity, id: String) {
            activity.startActivity(
                Intent(activity, FamilyMembersActivity::class.java)
                    .apply {
                        putExtra(REPORT_ID, id)
                    }
            )
        }
    }
}