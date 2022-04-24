package com.ksa.unticovid.core.extentions

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.ksa.unticovid.R
import com.ksa.unticovid.core.utils.CustomTypefaceSpan

fun TextView.setAppTitleSpanStyle(context: Context) {
    val appNamePartOneSpannableString = context.getString(R.string.app_name_first)
    val appNamePartTwoSpannableString = context.getString(R.string.app_name_last)

    val title = SpannableString("$appNamePartOneSpannableString $appNamePartTwoSpannableString")

    val appNamePartOneSpannableStart = title.indexOf(appNamePartOneSpannableString)

    val appNamePartTwoSpannableStart = title.indexOf(appNamePartTwoSpannableString)

    val semiBoldSpan: TypefaceSpan = CustomTypefaceSpan(
        "",
        Typeface.create(
            ResourcesCompat.getFont(context, R.font.roboto_bold),
            Typeface.NORMAL
        )
    )
    val lightSpan: TypefaceSpan = CustomTypefaceSpan(
        "",
        Typeface.create(
            ResourcesCompat.getFont(context, R.font.roboto_light),
            Typeface.NORMAL
        )
    )

    title.setSpan(
        semiBoldSpan,
        appNamePartOneSpannableStart,
        appNamePartOneSpannableStart + appNamePartOneSpannableString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    title.setSpan(
        ForegroundColorSpan(context.resources.getColor(R.color.colorPrimary)),
        appNamePartOneSpannableStart,
        appNamePartOneSpannableStart + appNamePartOneSpannableString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    title.setSpan(
        lightSpan,
        appNamePartTwoSpannableStart,
        appNamePartTwoSpannableStart + appNamePartTwoSpannableString.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = title
}

fun WebView.setupWebView() {
//    settings.javaScriptEnabled = true
    settings.javaScriptCanOpenWindowsAutomatically = true
    settings.pluginState = WebSettings.PluginState.ON
    settings.mediaPlaybackRequiresUserGesture = false
    webChromeClient = WebChromeClient()
}
