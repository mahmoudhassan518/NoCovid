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
    SpannableString: String,
    onSpannableClickListener: Action,
): SpannableString {
    val spannable = SpannableString(this)

    val spannableStart = spannable.indexOf(SpannableString)

    val registerNowClickableSpan = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = context.resources.getColor(R.color.colorPrimary)
            ds.isUnderlineText = true
        }
        override fun onClick(view: View) {
            onSpannableClickListener.invoke()
        }
    }
    spannable.setSpan(
        registerNowClickableSpan,
        spannableStart, // start
        spannableStart + SpannableString.length, // end
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )

    return spannable
}