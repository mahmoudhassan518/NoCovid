package com.ksa.unticovid.modules.questions.di

import com.ksa.unticovid.modules.questions.data.repository.QuestionsRepositoryImpl
import com.ksa.unticovid.modules.questions.domain.repository.QuestionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class QuestionsDataModule {

    @Binds
    abstract fun bindQuestionsRepository(impl: QuestionsRepositoryImpl): QuestionsRepository
}
