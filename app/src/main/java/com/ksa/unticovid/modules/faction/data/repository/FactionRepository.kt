package com.ksa.unticovid.modules.faction.data.repository

import com.ksa.unticovid.base.BaseRepository
import com.ksa.unticovid.modules.common.data.source.remote.ApiService
import com.ksa.unticovid.modules.common.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FactionRepository @Inject constructor(
    @IODispatcher ioDispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) : BaseRepository(ioDispatcher) {

    fun getFactions() = requestHandler { apiService.getFactions() }
}
