package com.vm.iqosmap.base

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.vm.iqosmap.BuildConfig
import com.vm.iqosmap.R
import com.vm.iqosmap.utils.MLD
import com.vm.iqosmap.utils.coroutines.CoroutinesCancel
import com.vm.iqosmap.utils.network.InternetUnreachableException
import com.vm.iqosmap.view.PlaceHolderView

@Suppress("ImplicitThis", "LeakingThis")
abstract class BaseViewModel(app: Application) : AndroidViewModel(app), LifecycleObserver {

    init {
        inject()
    }

    protected val coroutinesRemove = CoroutinesCancel()

    open val ldProgress = MLD<Boolean>().apply { value = false }
    open val ldHolderState = MLD<PlaceHolderView.HolderState>().apply { value = PlaceHolderView.HolderState.Progress }

    abstract fun inject()

    override fun onCleared() {
        coroutinesRemove.clear()

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