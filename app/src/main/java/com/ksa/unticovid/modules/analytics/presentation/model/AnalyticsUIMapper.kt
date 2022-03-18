package com.ksa.unticovid.modules.analytics.presentation.model

import com.ksa.unticovid.modules.analytics.domain.entity.AnalyticsEntity

fun AnalyticsEntity.toUIModel() = AnalyticsItemUIModel(
    id = id,
    date = date,
    image = image
)