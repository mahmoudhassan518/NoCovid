package com.ksa.unticovid.modules.main.report.presentation.model.mapper

import com.ksa.unticovid.R
import com.ksa.unticovid.modules.main.report.domain.entity.ReportEntity
import com.ksa.unticovid.modules.main.report.domain.entity.ReportPCREntity
import com.ksa.unticovid.modules.main.report.presentation.model.ReportCovidDataUIModel
import com.ksa.unticovid.modules.main.report.presentation.model.ReportItemUIModel
import com.ksa.unticovid.modules.main.report.presentation.model.ReportPCRUIModel

fun ReportEntity.toUIModel() = ReportItemUIModel(
    id = id,
    date = date,
    hasCovid = hasCovid,
    resultMessage= if (hasCovid) R.string.positive else R.string.negative,
    covidData = toCovidDataUIModel()
)

private fun ReportEntity.toCovidDataUIModel() = ReportCovidDataUIModel(
    doctorId = doctor?.id,
    doctorName = doctor?.name,
    doctorMobile = doctor?.mobile,
    doctorAddress = doctor?.address,
    pcrList = pcrList?.map { it.toUIModel() }
)

fun ReportPCREntity.toUIModel() = ReportPCRUIModel(
    id = id,
    testId = testId,
    image = image,
)