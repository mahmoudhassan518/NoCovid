package com.ksa.unticovid.modules.main.report.domain.entity

data class ReportEntity(
    val id: String,
    val date: String,
    val hasCovid: Boolean,
    val pcrList: List<ReportPCREntity>?,
    val doctor: ReportDoctorEntity?
)

data class ReportPCREntity(
    val id: String,
    val image: String,
    val testId: String,
)

data class ReportDoctorEntity(
    val id: String,
    val name: String,
    val mobile: String,
    val address: String,
)