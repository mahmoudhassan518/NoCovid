package com.ksa.unticovid.modules.upload.domain.repository

import com.ksa.unticovid.modules.upload.domain.entity.param.UploadReportImageParam
import kotlinx.coroutines.flow.Flow

interface UploadRepository {

    fun uploadReportImage(param: UploadReportImageParam): Flow<Unit>
}