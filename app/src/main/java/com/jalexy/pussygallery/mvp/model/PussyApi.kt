package com.jalexy.pussygallery.mvp.model

import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.mvp.model.entities.Image
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PussyApi {

    @Headers("x-api-key: ${PussyApplication.API_KEY}")
    @GET("images/search/?")
    fun getImages(
        @Query("size") size: String,
        @Query("order") order: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("format") format: String
    ): Observable<ArrayList<Image>>
}