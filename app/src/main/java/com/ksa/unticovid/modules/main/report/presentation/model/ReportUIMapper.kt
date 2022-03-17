package com.ksa.unticovid.modules.main.report.presentation.model

import com.ksa.unticovid.modules.main.report.domain.entity.ReportEntity

fun ReportEntity.toUIModel() = ReportItemUIModel(
    id = id,
    title = title,
    date = date
)
