package com.ksa.unticovid.core.extentions

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Insets
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.ksa.unticovid.BuildConfig
import com.ksa.unticovid.R
import com.tapadoo.alerter.Alerter
import java.io.File


fun Activity.showAlerterError(
    errorMessage: String,
    errorTitle: String? = null
) {
    Alerter.create(this)
        .setIcon(R.drawable.ic_failed)
        .setTitle(errorTitle ?: getString(R.string.error))
        .setTitleAppearance(R.style.AlertTextAppearance_Title)
        .setText(errorMessage)
        .setTextAppearance(R.style.AlertTextAppearance_Text)
        .setBackgroundColorInt(ContextCompat.getColor(applicationContext, R.color.colorError))
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
        .setBackgroundColorInt(ContextCompat.getColor(applicationContext, R.color.colorSuccess))
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

fun Activity.getTmpFileUri(): Uri {
    val tmpFile =
        File.createTempFile("tmp_image_file", ".png", this.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

    return FileProvider.getUriForFile(
        this,
        "${BuildConfig.APPLICATION_ID}.provider",
        tmpFile
    )
}

fun Activity.getBitmapFromUri(imageUri: Uri): Bitmap =
    BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))

fun Activity.hasCameraPermission() = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.CAMERA
) == PackageManager.PERMISSION_GRANTED

fun Activity.hasExternalStoragePermission() = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.READ_EXTERNAL_STORAGE
) == PackageManager.PERMISSION_GRANTED