package com.ksa.unticovid.modules.faction.presentation.model

data class FactionsUIModel(
    val factions: FactionsDataUIModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)

data class FactionsDataUIModel(val data: String)
