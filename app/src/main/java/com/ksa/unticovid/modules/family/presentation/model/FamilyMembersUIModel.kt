package com.ksa.unticovid.modules.family.presentation.model

data class FamilyMembersUIModel(
    val family: List<FamilyDataUIModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val emptyMessage: Int? = null
)

data class FamilyDataUIModel(
    var id: Int,
    var nationalIdentity: String,
    var age: String,
    var gender: Int,
    var mobile: String,
    var name: String,
    var relation: String,
)
