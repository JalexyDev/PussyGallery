package com.jalexy.pussygallery

import android.app.Application
import com.jalexy.pussygallery.di.components.ApiComponent
import com.jalexy.pussygallery.di.components.DaggerApiComponent
import com.jalexy.pussygallery.di.modules.AppModule
import com.jalexy.pussygallery.di.modules.PussyApiModule

class PussyApplication : Application() {

    companion object {
        const val BASE_PUSSY_URL = "https://api.thecatapi.com/v1/"
    }

    lateinit var apiComponent: ApiComponent

    override fun onCreate() {
        super.onCreate()

        apiComponent = DaggerApiComponent.builder()
            .appModule(AppModule(this))
            .pussyApiModule(PussyApiModule(BASE_PUSSY_URL))
            .build()
    }
}