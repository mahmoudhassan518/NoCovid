package com.ksa.unticovid.core.utils

import androidx.annotation.StringRes
import com.ksa.unticovid.R

data class UIError(
    @StringRes val title: Int? = null,
    @StringRes val msg: Int

) {
    companion object {

        fun getUnexpectedError(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.msgSomethingWentWrong
            )
        }

        fun failedToGetUserDataError(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.failedGetProfileError
            )
        }

        fun hasBlankFieldsError(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.emptyFields // R.string.msgWrong
            )
        }

        fun getInvalidNationalIdError(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.invalidNationalId
            )
        }

        fun getInvalidEmailAddressError(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.invalidEmailAddress
            )
        }

        fun getPasswordMissMatchError(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.passwordMissMatchMsg
            )
        }


        fun getPasswordLengthError(): UIError {
            return UIError(
                title = R.string.error,
                msg = R.string.passwordLengthErrorMsg
            )
        }
    }
}
