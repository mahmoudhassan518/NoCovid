package com.ksa.unticovid.modules.information.presentation.model.mapper

import com.ksa.unticovid.modules.information.data.model.InformationResponse
import com.ksa.unticovid.modules.information.presentation.model.InformationDataUIModel

fun InformationResponse.toUIModel() = InformationDataUIModel(data = data.text)
