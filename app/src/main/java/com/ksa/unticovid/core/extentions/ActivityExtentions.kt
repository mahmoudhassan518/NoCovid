package com.ksa.unticovid.core.extentions

import android.app.Activity
import com.ksa.unticovid.R
import com.tapadoo.alerter.Alerter

fun Activity.showAlerterError(
    errorMessage: String,
    errorTitle: String? = null
) {
    Alerter.create(this)
        .setIcon(R.drawable.ic_error)
        .setTitle(errorTitle ?: getString(R.string.error))
        .setTitleAppearance(R.style.AlertTextAppearance_Title)
        .setText(errorMessage)
        .setTextAppearance(R.style.AlertTextAppearance_Text)
        .setBackgroundColorInt(resources.getColor(R.color.colorError))
        .enableSwipeToDismiss()
        .show()
}

fun Activity.showAlerterSuccess(
    errorMessage: String
) {
    Alerter.create(this)
        .setIcon(R.drawable.ic_check)
        .setTitle(getString(R.string.done))
        .setTitleAppearance(R.style.AlertTextAppearance_Title)
        .setText(errorMessage)
        .setTextAppearance(R.style.AlertTextAppearance_Text)
        .setBackgroundColorInt(resources.getColor(R.color.colorSuccess))
        .enableSwipeToDismiss()
        .show()
}
