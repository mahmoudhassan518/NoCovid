package com.ksa.unticovid.modules.family.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class FamilyMembersUIModel(
    val family: MutableList<FamilyMemberDataUIModel> = mutableListOf(),
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val emptyMessage: Int? = null
)

@Parcelize
data class FamilyMemberDataUIModel(
    var id: Int,
    var nationalIdentity: String,
    var age: String,
    var gender: Int,
    var mobile: String,
    var name: String,
    var relation: String,
): Parcelable
