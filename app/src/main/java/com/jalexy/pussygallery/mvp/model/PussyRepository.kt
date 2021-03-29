package com.jalexy.pussygallery.mvp.model

import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.mvp.model.entities.Image
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PussyRepository {
    @Inject
    lateinit var apiManager: PussyApiManager

    init {
        PussyApplication.appComponent.inject(this)
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


//    fun getFavorites(): Flowable<ArrayList<MyPussy>> =
//        pussyDb.pussyDao().getAll()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//    fun getFavoritesBetween(start: Int, end: Int): Observable<ArrayList<MyPussy>> =
//        pussyDb.pussyDao().getPussiesBetween(start, end)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//    fun addToFavorite(pussy: MyPussy) = Completable.fromAction { pussyDb.pussyDao().insert(pussy) }
//
//    fun deleteFavorite(pussy: MyPussy) = Completable.fromAction { pussyDb.pussyDao().delete(pussy) }
}