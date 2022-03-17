package com.ksa.unticovid.modules.information.presentation.model

import androidx.annotation.StringRes

sealed class InformationEffects {
    data class ShowInformationError(@StringRes val message: Int) : InformationEffects()
}
