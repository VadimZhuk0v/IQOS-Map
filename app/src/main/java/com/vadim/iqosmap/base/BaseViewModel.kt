package com.vadim.iqosmap.base

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.vadim.iqosmap.BuildConfig
import com.vadim.iqosmap.R
import com.vadim.iqosmap.utils.MLD
import com.vadim.iqosmap.utils.network.InternetUnreachableException
import com.vadim.iqosmap.view.PlaceHolderView
import io.reactivex.disposables.CompositeDisposable

@Suppress("ImplicitThis")
abstract class BaseViewModel(app: Application) : AndroidViewModel(app), LifecycleObserver {

    init {
        inject()
    }

    protected val compositeDisposable = CompositeDisposable()

    open val ldProgress = MLD<Boolean>().apply { value = false }
    open val ldHolderState = MLD<PlaceHolderView.HolderState>().apply { value = PlaceHolderView.HolderState.Progress }

    abstract fun inject()

    override fun onCleared() {
        compositeDisposable.clear()

        super.onCleared()
    }

    open fun onError(throwable: Throwable) {
        if (BuildConfig.DEBUG)
            throwable.printStackTrace()

        if (throwable is InternetUnreachableException)
            showInternetConnectionError()

        hideProgress()
    }

    open fun showInternetConnectionError() {
        showMessage(getApplication<Application>().getString(R.string.error_internet_connection))
    }

    open fun showMessage(message: String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }

    open fun showProgress() {
        ldProgress.postValue(true)
    }

    open fun hideProgress() {
        ldProgress.postValue(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun viewOnCreate() {
        Log.d("View's Lifecycle", "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun viewOnResume() {
        Log.d("View's Lifecycle", "onResume")
    }

}