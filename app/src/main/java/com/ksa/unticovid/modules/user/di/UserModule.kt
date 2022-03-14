package com.ksa.unticovid.modules.user.di

import com.ksa.unticovid.modules.user.data.repository.UserRepositoryImpl
import com.ksa.unticovid.modules.user.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
