package com.ksa.unticovid.core.utils

import androidx.annotation.StringRes
import com.ksa.unticovid.R

data class UIError(
    @StringRes val title: Int? = null,
    @StringRes val msg: Int

) {
    companion object {

        fun getUnexpectedException(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.msgSomethingWentWrong
            )
        }

        fun failedToGetUserData(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.failedGetUserError
            )
        }

        fun hasBlankFields(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.emptyFields // R.string.msgWrong
            )
        }

        fun invalidNationalId(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.invalidNationalId
            )
        }
    }
}
