package com.jalexy.pussygallery.data.api

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jalexy.pussygallery.mvp.model.PussyApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiFactory @Inject constructor(
    gson: Gson,
    okHttpClient: OkHttpClient
) {

    private val retrofit = Retrofit.Builder()
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_PUSSY_URL)
    .client(okHttpClient)
    .build()

    val apiService: PussyApi = retrofit.create(PussyApi::class.java)

    companion object {
        private const val BASE_PUSSY_URL = "https://api.thecatapi.com/v1/"
    }
}