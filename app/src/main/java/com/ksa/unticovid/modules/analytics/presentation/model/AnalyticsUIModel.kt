package com.ksa.unticovid.modules.analytics.presentation.model

data class AnalyticsUIModel(
    val analytics: List<AnalyticsItemUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val refresh: Boolean = false,
    val errorMessage: Int? = null,
    val emptyMessage: Int? = null
)

data class AnalyticsItemUIModel(val id: String, val date: String, val image: String)
