package com.ksa.unticovid.modules.family.domain.enitiy

data class FamilyEntity(
    var id: Int,
    var nationalIdentity: String,
    var age: String,
    var gender: String,
    var mobile: String,
    var name: String,
    var relation: String,
)
