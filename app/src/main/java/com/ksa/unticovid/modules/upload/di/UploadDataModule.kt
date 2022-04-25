package com.ksa.unticovid.modules.upload.di

import com.ksa.unticovid.modules.upload.data.repository.UploadRepositoryImpl
import com.ksa.unticovid.modules.upload.domain.repository.UploadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UploadDataModule {

    @Binds
    abstract fun bindUploadRepository(impl: UploadRepositoryImpl): UploadRepository
}
