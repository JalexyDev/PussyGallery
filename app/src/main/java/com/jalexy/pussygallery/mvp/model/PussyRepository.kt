package com.jalexy.pussygallery.mvp.model

import com.jalexy.pussygallery.database.DbChangeListener
import com.jalexy.pussygallery.mvp.model.entities.Image
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

class PussyRepository(val apiManager: PussyApiManager, val dbManager: PussyFavoriteDbManager) {

    private val observers: ArrayList<DbChangeListener> by lazy {
        ArrayList<DbChangeListener>()
    }

    fun addDbChangeListener(listener: DbChangeListener) {
        observers.add(listener)
    }

    fun removeDbChangeListener(listener: DbChangeListener) {
        observers.remove(listener)
    }

    fun pussyRemovedFromFavorite(pussy: MyPussy) {
        pussy.setInFavorite(false)
        for (observer in observers) {
            observer?.removed(pussy)
        }
    }

    fun pussyAddedToFavorite(pussy: MyPussy) {
        pussy.setInFavorite(true)
        for (observer in observers) {
            observer?.added(pussy)
        }
    }

    fun getImages(
        size: String = "full",
        order: String = PussyApiManager.ORDER_ASC,
        limit: Int = 100,
        page: Int = 0,
        format: String = "json"
    ): Observable<ArrayList<Image>> =
        apiManager.getImages(size, order, limit, page, format)

    fun getFavorites(): Flowable<ArrayList<MyPussy>> =
        dbManager.getAllFavorites()

    fun getFavoriteByIdOrPussyId(pussyId: String = ""): Observable<MyPussy> =
        dbManager.getFavoritePussy(pussyId)

    fun addToFavorite(pussy: MyPussy): Completable =
        dbManager.addPussyToFavorite(pussy)

    fun deleteFavorite(pussy: MyPussy): Completable =
        dbManager.deletePussyFromFavorite(pussy)
}