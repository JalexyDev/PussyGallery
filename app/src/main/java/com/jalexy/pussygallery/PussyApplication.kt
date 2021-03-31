package com.jalexy.pussygallery

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.jalexy.pussygallery.di.components.*
import com.jalexy.pussygallery.di.modules.AppModule
import com.jalexy.pussygallery.di.modules.PussyApiModule
import com.jalexy.pussygallery.di.modules.PussyDbModule
import com.jalexy.pussygallery.di.modules.PussyRepositoryModule
import com.jalexy.pussygallery.mvp.model.PussyRepository

class PussyApplication : MultiDexApplication() {

    companion object {
        const val API_KEY = "8c26fac0-0ab5-4b1e-8a7e-74998f76e1d9"
        const val BASE_PUSSY_URL = "https://api.thecatapi.com/v1/"

        const val PREFERENCE_NAME = "APPLICATION_PREFERENCE"
        const val USER_ID_KEY = "USER_ID"

        var USER_ID: String? = null
        lateinit var appComponent: AppComponent
        lateinit var repositoryComponent: RepositoryComponent
        lateinit var fragmentComponent: FragmentComponent

    }

    override fun onCreate() {
        super.onCreate()

        val appModule = AppModule(this)

        appComponent = DaggerAppComponent.builder()
            .appModule(appModule)
            .pussyApiModule(PussyApiModule(BASE_PUSSY_URL))
            .pussyDbModule(PussyDbModule())
            .build()

        repositoryComponent = DaggerRepositoryComponent.builder()
            .pussyRepositoryModule(PussyRepositoryModule(PussyRepository()))
            .build()

        fragmentComponent = DaggerFragmentComponent.builder()
            .appModule(appModule)
            .build()

        val sharedPreferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        USER_ID = sharedPreferences.getString(USER_ID_KEY, "")

        USER_ID.isNullOrEmpty().let {
            USER_ID = generateUserId()
            val editor = sharedPreferences.edit()
            editor.putString(USER_ID_KEY, USER_ID)
            editor.apply()
        }
    }

    private fun generateUserId(): String {
        val length = (8..12).random()
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}