package com.ksa.unticovid.modules.result.domain.entity.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultParams(
    val resultText: String?,
    val isInfected: Boolean,
    val doctorNumber: String? = null,
    val doctorName: String? = null,
) : Parcelable
