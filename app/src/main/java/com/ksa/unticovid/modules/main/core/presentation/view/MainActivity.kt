package com.ksa.unticovid.modules.main.core.presentation.view

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.ksa.unticovid.R
import com.ksa.unticovid.base.BaseActivity
import com.ksa.unticovid.core.extentions.setAppTitleSpanStyle
import com.ksa.unticovid.databinding.ActivityMainBinding
import com.ksa.unticovid.modules.main.core.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    override fun setup() {
        initToolBar()
    }

    private fun initToolBar() {
        binder.tvAppName.setAppTitleSpanStyle(this)
    }

    companion object {

        fun startActivity(activity: Activity) {
            val mainIntent = Intent(activity, MainActivity::class.java)
            activity.startActivity(mainIntent)
            activity.finish()
        }
    }
}
