package com.ksa.unticovid.modules.upload.presentation.model

import androidx.annotation.StringRes

sealed class UploadEffects {
    data class ShowUploadError(@StringRes val message: Int) : UploadEffects()
    data class ShowUploadSuccess(@StringRes val message: Int) : UploadEffects()
}
