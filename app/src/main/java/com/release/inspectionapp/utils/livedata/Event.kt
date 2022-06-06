package com.release.inspectionapp.utils.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

class Event<out T>(private var content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onHandleEvent: (T) -> Unit
) {
    observe(owner) { it?.getContentIfNotHandled()?.let(onHandleEvent) }
}
