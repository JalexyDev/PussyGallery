package com.jalexy.pussygallery.di.components

import com.jalexy.pussygallery.di.modules.AppModule
import com.jalexy.pussygallery.di.modules.PussyApiModule
import com.jalexy.pussygallery.mvp.model.PussyRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PussyApiModule::class])
interface AppComponent {
    fun inject(repository: PussyRepository)
}