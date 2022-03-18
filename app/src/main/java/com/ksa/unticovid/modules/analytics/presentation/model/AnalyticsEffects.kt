package com.ksa.unticovid.modules.analytics.presentation.model

import androidx.annotation.StringRes
import com.ksa.unticovid.core.utils.UIError

sealed class AnalyticsEffects {
    data class ShowAnalyticsError(@StringRes val error: Int) : AnalyticsEffects()
    data class ShowAnalyticsSuccessfulAlert(@StringRes val message: Int) : AnalyticsEffects()
}
