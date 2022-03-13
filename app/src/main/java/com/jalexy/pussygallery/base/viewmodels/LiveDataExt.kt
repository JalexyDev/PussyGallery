package com.jalexy.pussygallery.base.viewmodels

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

@MainThread
inline fun <T : Any> LiveData<Event<T>>.observeEvents(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<Event<T>> {
    val observer = Observer<Event<T>> { event ->
        event.getContentIfNotHandled()?.let {
            onChanged(it)
        }
    }
    observe(owner, observer)
    return observer
}

@MainThread
fun MutableLiveData<Event<Unit>>.sendEvent() {
    value = Event(Unit)
}

@MainThread
fun <T : Any> MutableLiveData<Event<T>>.sendEvent(content: T) {
    value = Event(content)
}

@MainThread
fun <T> MutableLiveData<T>.reduce(reducer: (oldValue: T) -> T) {
    val oldValue = value ?: return
    value = reducer(oldValue)
}