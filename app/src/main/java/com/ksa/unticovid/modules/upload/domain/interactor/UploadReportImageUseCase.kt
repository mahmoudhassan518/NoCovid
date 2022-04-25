package com.ksa.unticovid.modules.upload.domain.interactor

import com.ksa.unticovid.base.BaseUseCase
import com.ksa.unticovid.modules.upload.domain.entity.param.UploadReportImageParam
import com.ksa.unticovid.modules.upload.domain.repository.UploadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadReportImageUseCase @Inject constructor(
    private val repository: UploadRepository,
) : BaseUseCase<UploadReportImageParam, Unit>() {

    override fun invoke(param: UploadReportImageParam): Flow<Unit> =
        repository.uploadReportImage(param)

}