package com.ksa.unticovid.modules.information.presentation.model

data class InformationUIModel(
    val information: InformationDataUIModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)

data class InformationDataUIModel(val data: String)
