package com.ksa.unticovid.modules.information.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.common.data.source.remote.ApiService
import com.ksa.unticovid.modules.common.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class InformationRepository @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : BaseRepository(ioDispatcher) {

    fun getInformation() = requestHandler { apiService.getInformation() }
}
