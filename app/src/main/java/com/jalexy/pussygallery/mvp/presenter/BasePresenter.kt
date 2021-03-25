package com.jalexy.pussygallery.mvp.presenter

import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.di.components.AppComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    private val disposables = CompositeDisposable()

    protected fun unsubscribeOnDestroy(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun getAppComponent(): AppComponent = PussyApplication.appComponent
}