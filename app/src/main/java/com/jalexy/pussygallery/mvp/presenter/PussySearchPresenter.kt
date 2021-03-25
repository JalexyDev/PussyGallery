package com.jalexy.pussygallery.mvp.presenter

import android.util.Log
import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.mvp.model.PussyApiManager
import com.jalexy.pussygallery.mvp.view.PussySearchFragment
import com.jalexy.pussygallery.mvp.view.SearchFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

@InjectViewState
class PussySearchPresenter : BasePresenter<SearchFragmentView>() {
    @Inject
    lateinit var apiManager: PussyApiManager

    init {
        PussyApplication.appComponent.inject(this)
    }

    fun getImages() {
        val disposable: Disposable = apiManager.getImages()
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                list?.let {
                    for (i in it.indices) {
                        Log.d("IdShmaidy#$i ->", it[i].id ?: "PUK")
                    }
                }
            }, { throwable -> Log.e("get request ", throwable?.message ?: "PUK") })

        unsubscribeOnDestroy(disposable)
    }
}