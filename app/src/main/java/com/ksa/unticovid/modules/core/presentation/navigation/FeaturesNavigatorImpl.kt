package com.ksa.unticovid.modules.core.presentation.navigation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ksa.unticovid.core.navigation.FeaturesNavigator
import com.ksa.unticovid.modules.faction.presentation.view.FactionsActivity
import com.ksa.unticovid.modules.family.presentation.view.AddFamilyMemberActivity
import com.ksa.unticovid.modules.family.presentation.view.FamilyMembersActivity
import com.ksa.unticovid.modules.information.presentation.view.InformationActivity
import com.ksa.unticovid.modules.main.core.presentation.view.MainActivity
import com.ksa.unticovid.modules.main.report.presentation.view.ReportDetailsActivity
import com.ksa.unticovid.modules.questions.presentation.view.QuestionsActivity
import com.ksa.unticovid.modules.user_management.core.presentation.view.UserManagementActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class FeaturesNavigatorImpl @Inject constructor(@ActivityContext private val context: Context) :
    FeaturesNavigator {
    override fun openUserManagementScreen() =
        UserManagementActivity.startActivity(context as AppCompatActivity)

    override fun openHomeScreen() =
        MainActivity.startActivity(context as AppCompatActivity)

    override fun openInformationScreen() =
        InformationActivity.startActivity(context as AppCompatActivity)

    override fun openFamilyMembersScreen(id: String) =
        FamilyMembersActivity.startActivity(context as AppCompatActivity, id)

    override fun openAddFamilyMemberScreen(id: String, withResult: Boolean) {
        if (withResult)
            AddFamilyMemberActivity.startActivityForResult(context as AppCompatActivity, id)
        else
            AddFamilyMemberActivity.startActivity(context as AppCompatActivity, id)
    }


    override fun openFactionScreen() =
        FactionsActivity.startActivity(context as AppCompatActivity)

    override fun openQuestionsScreen() =
        QuestionsActivity.startActivity(context as AppCompatActivity)

    override fun openReportDetailsScreen(id: String) =
        ReportDetailsActivity.startActivity(context as AppCompatActivity, id)

}
