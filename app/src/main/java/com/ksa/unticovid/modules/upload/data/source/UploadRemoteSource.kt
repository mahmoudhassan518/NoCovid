package com.ksa.unticovid.modules.upload.data.source

import com.ksa.unticovid.modules.core.data.source.remote.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UploadRemoteSource @Inject constructor(private val apiService: ApiService) {

    suspend fun uploadReportImage(image: MultipartBody.Part, id: String) =
        apiService.uploadReportImage(
            id.toRequestBody(),
            image
        )
}