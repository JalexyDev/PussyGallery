package com.jalexy.pussygallery.di.components

import com.jalexy.pussygallery.di.modules.AppModule
import com.jalexy.pussygallery.di.modules.PussyApiModule
import com.jalexy.pussygallery.mvp.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PussyApiModule::class])
interface ApiComponent {
    fun inject(activity: MainActivity)
}