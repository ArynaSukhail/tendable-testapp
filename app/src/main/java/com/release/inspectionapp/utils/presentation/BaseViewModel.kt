package com.release.inspectionapp.utils.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.release.inspectionapp.utils.NavAction
import com.release.inspectionapp.utils.livedata.Event
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {

    protected val _navigationEvent = MutableLiveData<Event<NavAction>>()
    val navigationEvent: LiveData<Event<NavAction>>
        get() = _navigationEvent

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.w("CoroutineException", exception.toString())
    }
}