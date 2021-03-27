package com.jalexy.pussygallery.mvp.model

import com.jalexy.pussygallery.PussyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PussyApiManager(private val apiService: PussyApi) {
    companion object {
        const val ORDER_RANDOM = "random"
        const val ORDER_ASC = "asc"
        const val ORDER_DESC = "desc"
    }

    fun getImage(imageId: String) =
        apiService.getImage(imageId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getImages(
        size: String,
        order: String,
        limit: Int,
        page: Int,
        format: String
    ) = apiService.getImages(size, order, limit, page, format)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getImagesWithBreed(
        size: String,
        order: String,
        limit: Int,
        page: Int,
        format: String,
        breedId: String
    ) = apiService.getImagesWithBreed(size, order, limit, page, format, breedId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun addToFavorite(
        imageId: String,
        userId: String
    ) = apiService.addToFavorite(imageId, userId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun deleteFromFavorite(favoriteId: String) =
        apiService.deleteFromFavorite(favoriteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}