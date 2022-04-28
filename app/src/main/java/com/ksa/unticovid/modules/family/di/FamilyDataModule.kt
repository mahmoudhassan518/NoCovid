package com.ksa.unticovid.modules.family.di

import com.ksa.unticovid.modules.family.data.repository.FamilyRepositoryImpl
import com.ksa.unticovid.modules.family.domain.repository.FamilyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FamilyDataModule {

    @Binds
    abstract fun bindFamilyRepository(impl: FamilyRepositoryImpl): FamilyRepository
}
