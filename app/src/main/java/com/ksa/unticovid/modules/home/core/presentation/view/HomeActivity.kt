package com.ksa.unticovid.modules.home.core.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.databinding.ActivityHomeBinding
import com.ksa.unticovid.modules.home.core.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(R.layout.activity_home) {

    override val viewModel: HomeViewModel by viewModels()

    override fun setup() {
    }

    companion object {

        fun startActivity(activity: Activity) {
            val mainIntent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(mainIntent)
            activity.finish()
        }
    }
}
