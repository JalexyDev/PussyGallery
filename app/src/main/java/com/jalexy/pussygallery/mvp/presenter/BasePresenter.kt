package com.jalexy.pussygallery.mvp.presenter

import com.jalexy.pussygallery.PussyApplication
import com.jalexy.pussygallery.di.components.RepositoryComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter{

    protected var isFree = true
    private val disposables = CompositeDisposable()

    protected fun unsubscribeOnDestroy(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun destroy() {
        disposables.clear()
    }

    protected fun getRepositoryComponent(): RepositoryComponent = PussyApplication.repositoryComponent
}