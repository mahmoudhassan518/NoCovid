package com.ksa.unticovid.modules.analytics.data.model.mapper

import com.ksa.unticovid.modules.analytics.data.model.AnalyticsResponse
import com.ksa.unticovid.modules.analytics.domain.entity.AnalyticsEntity

fun AnalyticsResponse.toEntity() = AnalyticsEntity(
    id = id,
    date = date,
    image = image
)