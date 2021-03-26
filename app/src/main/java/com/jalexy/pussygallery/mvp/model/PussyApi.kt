package com.jalexy.pussygallery.mvp.model

import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.mvp.model.entities.Image
import com.jalexy.pussygallery.mvp.model.responses.BaseResponse
import com.jalexy.pussygallery.mvp.model.responses.FavoriteOkResponse
import io.reactivex.Observable
import retrofit2.http.*

interface PussyApi {

    @Headers("x-api-key: ${PussyApplication.API_KEY}")
    @GET("images/{image_id}")
    fun getImage(@Path(value = "image_id", encoded = true) imageId: String): Observable<Image>

    @Headers("x-api-key: ${PussyApplication.API_KEY}")
    @GET("images/search/?")
    fun getImages(
        @Query("size") size: String,
        @Query("order") order: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("format") format: String
    ): Observable<ArrayList<Image>>

    // нет значения по умолчанию для породы, так что пришлось делать отдельный метод
    @Headers("x-api-key: ${PussyApplication.API_KEY}")
    @GET("images/search/?")
    fun getImagesWithBreed(
        @Query("size") size: String,
        @Query("order") order: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("format") format: String,
        @Query("breed_id") breedId: String
    ): Observable<ArrayList<Image>>

    @Headers("x-api-key: ${PussyApplication.API_KEY}")
    @POST
    fun addToFavorite(
        @Field("image_id") imageId: String,
        @Field("sub_id") userId: String
    ): Observable<FavoriteOkResponse>

    @Headers("x-api-key: ${PussyApplication.API_KEY}")
    @GET("favourites/{favourite_id}")
    fun deleteFromFavorite(
        @Path(value = "favourite_id", encoded = true) favoriteId: String
    ): Observable<BaseResponse>
}