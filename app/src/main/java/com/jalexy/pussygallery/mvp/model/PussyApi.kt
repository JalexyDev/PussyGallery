package com.jalexy.pussygallery.mvp.model

import io.reactivex.Observable
import retrofit2.http.GET

interface PussyApi {

    @GET("images/search")
    fun getImages() : Observable<ArrayList<Image>>
}