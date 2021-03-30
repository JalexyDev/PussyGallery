package com.jalexy.pussygallery.mvp.model

import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.database.DbChangeListener
import com.jalexy.pussygallery.mvp.model.entities.Image
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PussyRepository {
    @Inject
    lateinit var apiManager: PussyApiManager

    @Inject
    lateinit var dbManager: PussyFavoriteDbManager

    private val observers: ArrayList<DbChangeListener> by lazy {
        ArrayList<DbChangeListener>()
    }

    init {
        PussyApplication.appComponent.inject(this)
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getFavorites(): Flowable<ArrayList<MyPussy>> =
        dbManager.getAllFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getFavoriteByIdOrPussyId(id: Int = -1, pussyId: String = ""): Observable<MyPussy> =
        dbManager.getFavoritePussy(id, pussyId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun addToFavorite(pussy: MyPussy): Completable =
        dbManager.addPussyToFavorite(pussy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteFavorite(pussy: MyPussy): Completable =
        dbManager.deletePussyFromFavorite(pussy)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}