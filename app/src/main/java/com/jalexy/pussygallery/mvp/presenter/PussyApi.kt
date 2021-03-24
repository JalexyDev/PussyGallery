package com.jalexy.pussygallery.mvp.presenter

import com.jalexy.pussygallery.mvp.model.Image
import retrofit2.Call
import retrofit2.http.GET

interface PussyApi {

    @GET("images/search")
    fun getImages() : Call<ArrayList<Image>>
}