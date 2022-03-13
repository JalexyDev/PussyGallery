package com.jalexy.pussygallery.mvp.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jalexy.pussygallery.base.viewmodels.Event
import com.jalexy.pussygallery.base.viewmodels.sendEvent
import com.jalexy.pussygallery.data.database.DbChangeListener
import com.jalexy.pussygallery.mvp.model.PussyRepository
import com.jalexy.pussygallery.mvp.model.entities.MyPussy
import com.jalexy.pussygallery.mvp.model.entities.MyPussy.Companion.FALSE
import com.jalexy.pussygallery.mvp.view.models.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class PussySearchViewModel @Inject constructor(
    private val repository: PussyRepository
) : ViewModel(), DbChangeListener {

    private val myPussyItemsCache = mutableListOf<MyPussy>()
    private var isFree = true
    private var page = 0

    private val disposables = CompositeDisposable()

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private val _pussiesState = MutableLiveData<List<MyPussy>>()
    val pussiesState: LiveData<List<MyPussy>> = _pussiesState

    private val _refreshEvent = MutableLiveData<Event<Unit>>()
    val refreshEvent: LiveData<Event<Unit>> = _refreshEvent

    private val _loadItemsEvent = MutableLiveData<Event<Unit>>()
    val loadItemsEvent: LiveData<Event<Unit>> = _loadItemsEvent

    private val _updateItemEvent = MutableLiveData<Event<MyPussy>>()
    val updateItemEvent: LiveData<Event<MyPussy>> = _updateItemEvent

    init {
        fragmentOpened()
        registerOnUpdates()
    }

    private fun fragmentOpened() {
        _screenState.value = ScreenState.Loading
    }

    fun fragmentStarted(isLoaded: Boolean) {
        if (myPussyItemsCache.isEmpty()) {
            getPussies()
        } else{
            if (isLoaded.not()) {
                _pussiesState.value = myPussyItemsCache
            }
            _screenState.value = ScreenState.Content
        }
    }

    private fun getPussies() {
        isFree = false

        val disposable: Disposable = repository.getImages(page = page)
            .subscribe(
                { list ->
                    val portion = list.map {
                        MyPussy(it.id, it.subId ?: "", it.url, FALSE)
                    }

                    myPussyItemsCache.addAll(portion)

                    _pussiesState.value = myPussyItemsCache
                    _screenState.value = ScreenState.Content
                    isFree = true
                },
                { throwable ->
                    Log.e("get request ", throwable?.message ?: "PUK")
                    _screenState.value = ScreenState.Error()
                    isFree = true
                })

        unsubscribeOnDestroy(disposable)
    }

    fun retryLoad() {
        getPussies()
    }

    fun scrolledToEnd() {
        if (isFree) {
            _loadItemsEvent.sendEvent()
            page++
            getPussies()
        }
    }

    fun refreshFragment() {
        page = 0
        _screenState.value = ScreenState.Loading
        myPussyItemsCache.clear()
        _refreshEvent.sendEvent()
    }

    override fun removed(pussy: MyPussy) {
        updateCachedPussyIsFavorite(pussy, false)
        _updateItemEvent.sendEvent(pussy)
    }

    override fun added(pussy: MyPussy) {
        updateCachedPussyIsFavorite(pussy, true)
        _updateItemEvent.sendEvent(pussy)
    }

    override fun registerOnUpdates() {
        repository.addDbChangeListener(this)
    }

    private fun updateCachedPussyIsFavorite(pussy: MyPussy, isFavorite: Boolean) {
        val cachedPos = myPussyItemsCache.indexOf(pussy)

        if (cachedPos != -1) {
            myPussyItemsCache[cachedPos].setInFavorite(isFavorite)
        }
    }

    private fun unsubscribeOnDestroy(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun setFavoriteState(pussy: MyPussy,  callback: (Boolean) -> Unit) {
        val disposable: Disposable =
            repository.getFavoriteByIdOrPussyId(pussy.pussyId)
                .subscribe(
                    {
                        if (it != MyPussy.EMPTY_PUSSY) {
                            callback(it != null)
                            pussy.setInFavorite(it != null)
                        } else {
                            callback(false)
                        }
                    },
                    {
                        callback(false)
                    })

        unsubscribeOnDestroy(disposable)
    }

    fun favoriteClicked(pussy: MyPussy, callback: (Boolean) -> Unit) {
        val isFavorite = pussy.isInFavorite()

        val completable = if (isFavorite) {
            repository.deleteFavorite(pussy)
        } else {
            repository.addToFavorite(pussy)
        }

        val disposable: Disposable =
            completable
                .doOnComplete {
                    pussy.setInFavorite(!isFavorite)

                    if (isFavorite) {
                        repository.pussyRemovedFromFavorite(pussy)
                    } else {
                        repository.pussyAddedToFavorite(pussy)
                    }
                }
                .doOnError {
                    Log.e("Base Presenter", it.message ?: "presenter exception")
                    callback(isFavorite)
                }
                .subscribe()

        unsubscribeOnDestroy(disposable)
    }
}