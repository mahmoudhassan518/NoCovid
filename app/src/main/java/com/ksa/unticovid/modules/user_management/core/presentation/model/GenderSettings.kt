package com.ksa.unticovid.modules.user_management.core.presentation.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class GenderSettings(
    @DrawableRes val maleBackground: Int,
    @ColorRes val maleColor: Int,
    @DrawableRes val femaleBackground: Int,
    @ColorRes val femaleColor: Int,
)