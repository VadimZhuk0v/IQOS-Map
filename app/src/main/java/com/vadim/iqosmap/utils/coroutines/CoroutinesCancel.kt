package com.vadim.iqosmap.utils.coroutines

class CoroutinesCancel : ArrayList<CoroutinesHelper>() {

    fun cancel() {
        this.forEach { it.cancel() }
    }

}