package com.vadmax.iqosmap.base

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.vadmax.iqosmap.BuildConfig
import com.vadmax.iqosmap.R
import com.vadmax.iqosmap.utils.MLD
import com.vadmax.iqosmap.utils.coroutines.CoroutinesHelper
import com.vadmax.iqosmap.utils.network.InternetUnreachableException
import com.vadmax.iqosmap.view.PlaceHolderView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import kotlin.coroutines.CoroutineContext

@Suppress("ImplicitThis", "LeakingThis")
abstract class BaseViewModel<RP : BaseRepository>(app: Application) : AndroidViewModel(app), LifecycleObserver,
                                                                      KoinComponent {

    protected abstract val repository: RP


    val coroutinesHelper get() = CoroutinesHelper(getApplication(), viewModelScope)

    open val ldProgress = MLD<Boolean>().apply { value = false }
    open val ldHolderState = MLD<PlaceHolderView.HolderState>().apply { value = PlaceHolderView.HolderState.Progress }


    fun launch(
        onError: (e: Throwable) -> Unit = ::onError,
        checkInternetConnection: Boolean = true,
        coroutineContext: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ) = coroutinesHelper.launch(
        onError,
        checkInternetConnection,
        coroutineContext,
        block
    )

    open fun onError(throwable: Throwable) {
        if (BuildConfig.DEBUG)
            throwable.printStackTrace()

        if (throwable is InternetUnreachableException)
            showInternetConnectionError()

        hideProgress()
    }

    open fun showInternetConnectionError() {
        val errorMessage = getString(R.string.error_internet_connection)

        if (ldHolderState.value == PlaceHolderView.HolderState.Hide)
            showMessage(errorMessage)
        else
            showErrorHolder(errorMessage)
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

    @Suppress("MemberVisibilityCanBePrivate")
    fun showErrorHolder(message: String, @DrawableRes imageId: Int = 0, needRepeat: Boolean = false) {
        ldHolderState.postValue(PlaceHolderView.HolderState.Error(message, imageId, needRepeat))
    }

    private fun getString(@StringRes strId: Int, vararg params: String) =
        getApplication<Application>().getString(strId, params)

    private fun getString(@StringRes strId: Int) = getApplication<Application>().getString(strId)

}