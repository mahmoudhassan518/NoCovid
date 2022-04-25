package com.ksa.unticovid.modules.upload.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.core.utils.FileUtils
import com.ksa.unticovid.modules.core.di.IODispatcher
import com.ksa.unticovid.modules.upload.data.source.UploadRemoteSource
import com.ksa.unticovid.modules.upload.domain.entity.param.UploadReportImageParam
import com.ksa.unticovid.modules.upload.domain.repository.UploadRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val fileUtils: FileUtils,
    private val source: UploadRemoteSource
) : UploadRepository, BaseRepository(dispatcher) {

    override fun uploadReportImage(param: UploadReportImageParam): Flow<Unit> = requestHandler {
        source.uploadReportImage(id = param.id, image = param.bitmap.getPartFromBitmap())
    }

    private fun Bitmap.getPartFromBitmap(): MultipartBody.Part {
        val file = fileUtils.toFile(this)
        val requestBody: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }


}