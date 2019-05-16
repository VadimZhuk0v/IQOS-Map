package com.vadmax.iqosmap.utils.live_data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

class SingleLiveData<T> {

    private val liveData =   MutableLiveData<Event<T>>()

    val value: T?
    get() = liveData.value?.peekContent()

    fun postValue(value: T) {
        liveData.postValue(Event(value))
    }

    fun observe(owner: LifecycleOwner, observer: EventObserver<T>){
        liveData.observe(owner, observer)
    }

    fun observe(owner: LifecycleOwner, observer:(value: T)-> Unit) {
        liveData.observe(owner, EventObserver { observer(it) })
    }

}