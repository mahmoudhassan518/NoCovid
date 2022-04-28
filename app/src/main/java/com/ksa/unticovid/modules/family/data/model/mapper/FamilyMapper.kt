package com.ksa.unticovid.modules.family.data.model.mapper

import com.ksa.unticovid.modules.family.data.model.FamilyDataResponse
import com.ksa.unticovid.modules.family.data.model.SubmitFamilyMemberRequest
import com.ksa.unticovid.modules.family.domain.enitiy.FamilyEntity
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam

fun AddFamilyMemberParam.toRequest() = SubmitFamilyMemberRequest(
    identity = identity,
    age = age,
    mobile = mobile,
    relation = relation,
    gender = gender,
    name = name,
)

fun FamilyDataResponse.toEntity() = FamilyEntity(
    id = id,
    nationalIdentity = identity,
    age = age,
    mobile = mobile,
    relation = relation,
    gender = gender,
    name = name,
)
