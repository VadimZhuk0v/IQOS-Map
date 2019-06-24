package com.vadmax.iqosmap.utils.live_data

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveData<T> : LiveData<T>() {

    private val liveData = MutableLiveData<Event<T>>()

    override fun getValue(): T? {
        return liveData.value?.peekContent()
    }

    override fun setValue(value: T) {
        liveData.value = Event(value)
    }

    public override fun postValue(value: T) {
        liveData.postValue(Event(value))
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        liveData.observe(owner, EventObserver { observer.onChanged(it) })
    }

    fun observe(owner: LifecycleOwner, event:(value: T)-> Unit) = observe(owner, Observer { event(it) })

}