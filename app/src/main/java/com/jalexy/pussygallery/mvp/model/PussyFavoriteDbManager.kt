package com.jalexy.pussygallery.mvp.model

import com.jalexy.pussygallery.database.DatabaseHandler
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PussyFavoriteDbManager(private val databaseHandler: DatabaseHandler) {

    fun getAllFavorites(): Flowable<ArrayList<MyPussy>> =
        Flowable.just(databaseHandler.getAllFavorites())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun addPussyToFavorite(pussy: MyPussy) =
        Completable.fromAction { databaseHandler.addFavoritePussy(pussy) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getFavoritePussy(pussyId: String): Observable<MyPussy> =
        Observable.just(databaseHandler.getFavoritePussy(pussyId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deletePussyFromFavorite(pussy: MyPussy) =
        Completable.fromAction { databaseHandler.deletePussyFromFavorites(pussy) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}