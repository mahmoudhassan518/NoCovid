package com.ksa.unticovid.modules.faction.presentation.model.mapper

import com.ksa.unticovid.modules.faction.data.model.FactionsResponse
import com.ksa.unticovid.modules.faction.presentation.model.FactionsDataUIModel

fun FactionsResponse.toUIModel() = FactionsDataUIModel(data = data.text)
