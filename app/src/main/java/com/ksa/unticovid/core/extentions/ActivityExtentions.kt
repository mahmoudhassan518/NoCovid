package com.ksa.unticovid.core.extentions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Insets
import com.ksa.unticovid.R
import com.tapadoo.alerter.Alerter
import android.util.DisplayMetrics
import android.view.WindowInsets

import android.view.WindowMetrics

import android.os.Build

import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


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

fun Activity.getScreenHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.height() - insets.top - insets.bottom
    } else {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
}
fun Activity.getScreenWidth(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

 fun Activity.isTablet(): Boolean {
    return ((resources.configuration.screenLayout
            and Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE)
}

 fun Activity.callPhoneNumber(phone: String) {
    val callIntent = Intent(Intent.ACTION_DIAL)
    callIntent.data = Uri.parse("tel:$phone")
    startActivity(callIntent)
}