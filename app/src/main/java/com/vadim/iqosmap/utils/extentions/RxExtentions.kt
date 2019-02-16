package com.vadim.iqosmap.utils.extentions

import android.content.Context
import com.vadim.iqosmap.utils.network.InternetUnreachableException
import com.vadim.iqosmap.utils.network.NetworkUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.checkInternetConnection(context: Context?): Single<T> {
    return Single.fromCallable { NetworkUtils.isInternetAvailable(context) }
        .flatMap {
            if (NetworkUtils.isInternetAvailable(context).not()) {
                Single.error<T>(InternetUnreachableException())
            } else this
        }
}