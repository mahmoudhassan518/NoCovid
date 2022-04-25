package com.ksa.unticovid.modules.upload.domain.entity.param

import android.graphics.Bitmap
import android.net.Uri

data class UploadReportImageParam(val bitmap: Bitmap, val imageUri: Uri, val id: String)
