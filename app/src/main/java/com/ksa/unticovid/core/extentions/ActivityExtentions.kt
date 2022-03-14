package com.ksa.unticovid.core.extentions

import android.app.Activity
import com.ksa.unticovid.R
import com.tapadoo.alerter.Alerter

fun Activity.showAlerterError(
    errorMessage: String
) {
    Alerter.create(this)
        .setIcon(R.drawable.ic_error)
        .setTitle(getString(R.string.error))
        .setTitleAppearance(R.style.AlertTextAppearance_Title)
        .setText(errorMessage)
        .setTextAppearance(R.style.AlertTextAppearance_Text)
        .setBackgroundColorInt(resources.getColor(R.color.colorError))
        .enableSwipeToDismiss()
        .show()

}