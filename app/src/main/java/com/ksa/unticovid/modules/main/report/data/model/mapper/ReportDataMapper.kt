package com.ksa.unticovid.modules.main.report.data.model.mapper

import com.ksa.unticovid.modules.main.report.data.model.ReportResponse
import com.ksa.unticovid.modules.main.report.domain.entity.ReportEntity

fun ReportResponse.toEntity() = ReportEntity(
    id = id,
    title = title,
    date = date
)
