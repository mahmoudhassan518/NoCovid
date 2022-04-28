package com.ksa.unticovid.modules.family.presentation.model.mapper

import com.ksa.unticovid.core.extentions.getGenderFromString
import com.ksa.unticovid.modules.family.domain.enitiy.FamilyEntity
import com.ksa.unticovid.modules.family.presentation.model.FamilyDataUIModel
import com.ksa.unticovid.modules.user_management.user.domain.entity.GenderType

fun FamilyEntity.toUIModel() = FamilyDataUIModel(
    id = id,
    nationalIdentity = nationalIdentity,
    age = age,
    mobile = mobile,
    relation = relation,
    gender = gender.getGenderFromString()!!,
    name = name,
)
