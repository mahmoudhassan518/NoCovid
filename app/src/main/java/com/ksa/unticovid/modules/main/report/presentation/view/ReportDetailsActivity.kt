package com.ksa.unticovid.modules.main.report.presentation.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ksa.unticovid.R
import com.ksa.unticovid.core.extentions.isNull
import com.ksa.unticovid.modules.main.report.presentation.model.ReportCovidDataUIModel
import com.ksa.unticovid.modules.upload.presentation.dialog.UploadDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportDetailsActivity : AppCompatActivity() {

    private var uploadDialog: UploadDialog? = null

    private val covidData: ReportCovidDataUIModel by lazy {
        intent.getParcelableExtra(
            REPORT_ITEM
        )!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_details)


    }


    private fun showUploadDialog() {
        uploadDialog.isNull {
            uploadDialog = UploadDialog.newInstance(

            )
            uploadDialog?.show(
                supportFragmentManager,
                UploadDialog::class.java.name
            )
            uploadDialog?.onDismissListener = {
                uploadDialog = null
            }
        }
    }

    companion object {
        private const val REPORT_ITEM = "ANALYTICS_ITEM"
        fun startActivity(activity: Activity, ANALYTICS_ITEM: ReportCovidDataUIModel) {
            activity.startActivity(
                Intent(activity, ReportDetailsActivity::class.java)
                    .apply {
                        putExtra(REPORT_ITEM, ANALYTICS_ITEM)
                    }
            )
        }
    }
}