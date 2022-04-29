package com.ksa.unticovid.modules.family.presentation.model.mapper

import com.ksa.unticovid.core.extentions.getGenderFromString
import com.ksa.unticovid.modules.family.domain.enitiy.FamilyMemberEntity
import com.ksa.unticovid.modules.family.presentation.model.FamilyMemberDataUIModel

fun FamilyMemberEntity.toUIModel() = FamilyMemberDataUIModel(
    id = id,
    nationalIdentity = nationalIdentity,
    age = age,
    mobile = mobile,
    relation = relation,
    gender = gender.getGenderFromString()!!,
    name = name,
)
