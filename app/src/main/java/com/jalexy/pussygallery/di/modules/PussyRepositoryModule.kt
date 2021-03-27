package com.jalexy.pussygallery.di.modules

import com.jalexy.pussygallery.mvp.model.PussyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PussyRepositoryModule(private val repository: PussyRepository) {
    @Provides
    @Singleton
    fun provideRepository() = repository
}