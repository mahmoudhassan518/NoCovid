package com.ksa.unticovid.core.extentions

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.ksa.unticovid.R
import com.ksa.unticovid.core.utils.Action

fun String.buildSignUpString(
    context: Context,
    onSingUpClickListener: Action,
): SpannableString {
    val spannable = SpannableString(this)
    val registerNow = context.getString(R.string.registerNow)

    val registerNowStart = spannable.indexOf(registerNow)

    val registerNowClickableSpan = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = context.resources.getColor(R.color.colorPrimary)
            ds.isUnderlineText = true
        }
        override fun onClick(view: View) {
            onSingUpClickListener.invoke()
        }
    }
    spannable.setSpan(
        registerNowClickableSpan,
        registerNowStart, // start
        registerNowStart + registerNow.length, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )

    return spannable
}
