package com.ksa.unticovid.base

import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<PARAM, TYPE> {
    abstract operator fun invoke(param: PARAM): Flow<TYPE>
}
