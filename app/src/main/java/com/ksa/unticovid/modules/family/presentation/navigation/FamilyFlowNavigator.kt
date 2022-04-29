package com.ksa.unticovid.modules.family.presentation.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.ksa.unticovid.modules.family.presentation.model.FamilyMemberDataUIModel
import com.ksa.unticovid.modules.family.presentation.view.FamilyMembersActivity.Companion.NEW_MEMBER_DATA
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class FamilyFlowNavigator @Inject constructor(@ActivityContext private val context: Context) {


    fun openFamilyMembersScreen(member: FamilyMemberDataUIModel) =
        (context as Activity).setResult(Activity.RESULT_OK, getFamilyMemberIntent(member)).also {
            context.finish()
        }

    private fun getFamilyMemberIntent(member: FamilyMemberDataUIModel): Intent =
        Intent().apply {
            putExtra(NEW_MEMBER_DATA, member)
        }
}
