package com.ksa.unticovid.modules.family.data.model.mapper

import com.ksa.unticovid.modules.family.data.model.FamilyDataResponse
import com.ksa.unticovid.modules.family.data.model.AddFamilyMemberRequest
import com.ksa.unticovid.modules.family.domain.enitiy.FamilyMemberEntity
import com.ksa.unticovid.modules.family.domain.enitiy.AddFamilyMemberParam

fun AddFamilyMemberParam.toRequest() = AddFamilyMemberRequest(
    identity = identity,
    age = age,
    mobile = mobile,
    relation = relation,
    gender = gender,
    name = name,
    reportId = reportId
)

fun FamilyDataResponse.toEntity() = FamilyMemberEntity(
    id = id,
    nationalIdentity = identity,
    age = age,
    mobile = mobile,
    relation = relation,
    gender = gender,
    name = name,
)
