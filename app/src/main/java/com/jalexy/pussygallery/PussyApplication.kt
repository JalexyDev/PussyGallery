package com.jalexy.pussygallery

import androidx.multidex.MultiDexApplication
import com.jalexy.pussygallery.di.components.AppComponent
import com.jalexy.pussygallery.di.components.DaggerAppComponent
import com.jalexy.pussygallery.di.modules.AppModule
import com.jalexy.pussygallery.di.modules.PussyApiModule

class PussyApplication : MultiDexApplication() {

    companion object {
        const val BASE_PUSSY_URL = "https://api.thecatapi.com/v1/"

        lateinit var appComponent: AppComponent
    }



    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .pussyApiModule(PussyApiModule(BASE_PUSSY_URL))
            .build()
    }
}