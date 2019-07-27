package com.vadmax.iqosmap.utils.extentions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> MutableLiveData<T>.observe(owner: LifecycleOwner, block: (value: T) -> Unit) {
    this.observe(owner, Observer { block(it) })
}