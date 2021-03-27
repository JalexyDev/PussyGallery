package com.jalexy.pussygallery.mvp.model

import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.mvp.model.entities.Image
import com.jalexy.pussygallery.mvp.model.responses.BaseResponse
import com.jalexy.pussygallery.mvp.model.responses.FavoriteOkResponse
import io.reactivex.Observable
import javax.inject.Inject

class PussyRepository {
    @Inject
    lateinit var apiManager: PussyApiManager

    init {
        PussyApplication.appComponent.inject(this)
    }

    fun getImage(imageId: String): Observable<Image> {
        return apiManager.getImage(imageId)
    }

    fun getImages(
        size: String = "full",
        order: String = PussyApiManager.ORDER_RANDOM,
        limit: Int = 10,
        page: Int = 0,
        format: String = "json"
    ): Observable<ArrayList<Image>> {
        return apiManager.getImages(size, order, limit, page, format)
    }

    fun getImagesWithBreed(
        size: String = "full",
        order: String = PussyApiManager.ORDER_RANDOM,
        limit: Int = 10,
        page: Int = 0,
        format: String = "json",
        breedId: String
    ): Observable<ArrayList<Image>> {
        return apiManager.getImagesWithBreed(size, order, limit, page, format, breedId)
    }

    fun addToFavorite(
        imageId: String,
        userId: String = PussyApplication.USER_ID!!
    ): Observable<FavoriteOkResponse> {
        return  apiManager.addToFavorite(imageId, userId)
    }

    fun deleteFromFavorite(favoriteId: String): Observable<BaseResponse> {
        return apiManager.deleteFromFavorite(favoriteId)
    }
}