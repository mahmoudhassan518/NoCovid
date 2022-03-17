package com.ksa.unticovid.modules.faction.presentation.model

import androidx.annotation.StringRes

sealed class FactionsEffects {
    data class ShowFactionsError(@StringRes val message: Int) : FactionsEffects()
}
