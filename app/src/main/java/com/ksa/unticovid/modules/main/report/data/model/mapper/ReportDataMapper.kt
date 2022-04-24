package com.ksa.unticovid.modules.main.report.data.model.mapper

import com.ksa.unticovid.modules.main.report.data.model.ReportDataResponse
import com.ksa.unticovid.modules.main.report.data.model.ReportDoctorResponse
import com.ksa.unticovid.modules.main.report.data.model.ReportPCRResponse
import com.ksa.unticovid.modules.main.report.domain.entity.ReportDoctorEntity
import com.ksa.unticovid.modules.main.report.domain.entity.ReportEntity
import com.ksa.unticovid.modules.main.report.domain.entity.ReportPCREntity

fun ReportDataResponse.toEntity() = ReportEntity(
    id = id.toString(),
    date = createAt,
    hasCovid = covidStatus,
    doctor = doctor?.toEntity(),
    pcrList = pcrs?.map { it.toEntity() }
)

fun ReportDoctorResponse.toEntity() = ReportDoctorEntity(
    id = id.toString(), name = name, mobile = mobile, address = address
)

fun ReportPCRResponse.toEntity() = ReportPCREntity(
    id = id.toString(), image = image, testId = image
)