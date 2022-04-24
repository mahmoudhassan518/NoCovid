package com.ksa.unticovid.modules.user_management.core.presentation.model.mapper

import com.ksa.unticovid.R
import com.ksa.unticovid.modules.user_management.core.presentation.model.GenderSettings
import com.ksa.unticovid.modules.user_management.user.domain.entity.GenderType

fun GenderType.genderUISettings(): GenderSettings = when (this) {
    GenderType.MALE -> GenderSettings(
        maleBackground = R.drawable.bg_enabled_button,
        maleColor = R.color.white,
        femaleBackground = R.drawable.bg_transparent,
        femaleColor = R.color.black,
    )
    GenderType.FEMALE -> GenderSettings(
        maleBackground = R.drawable.bg_transparent,
        maleColor = R.color.black,
        femaleBackground = R.drawable.bg_enabled_button,
        femaleColor = R.color.white,
    )
}