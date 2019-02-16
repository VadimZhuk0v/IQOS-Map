package com.vadim.iqosmap.utils.coroutines

import android.content.Context
import android.os.Build
import com.vadim.iqosmap.BuildConfig
import com.vadim.iqosmap.utils.network.InternetUnreachableException
import com.vadim.iqosmap.utils.network.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CoroutinesHelper(private val context: Context, private var coroutineContext: CoroutineContext = Dispatchers.IO) {

    private var currentJob: Job? = null
    private var isCheckInternetConnection = true

    private var coroutinescancel: CoroutinesCancel? = null
        set(value) {
            if (field != null && field!!.contains(this)) {
                field?.remove(this)
            }

            value?.add(this)
            field = value
        }

    fun launch(
        onError: (e: Exception) -> Unit = ::defaultOnError,
        block: suspend CoroutineScope.() -> Unit
    ): CoroutinesHelper {
        if (currentJob != null && currentJob!!.isCancelled.not()) {
            currentJob?.cancel()
        }

        currentJob = GlobalScope.launch(coroutineContext) {
            try {
                if (isCheckInternetConnection)
                    checkInternetConnection(context)

                block()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }

        addToRemove()

        return this
    }

    private fun addToRemove() {
        coroutinescancel?.let {
            if (it.contains(this).not())
                it.add(this)
        }
    }

    private fun defaultOnError(e: Exception) {
        if (BuildConfig.DEBUG)
            e.printStackTrace()
    }

    fun cancel(): CoroutinesHelper {
        currentJob?.let {
            if (it.isCancelled.not())
                it.cancel()
        }
        return this
    }

    fun setCancel(coroutinesCancel: CoroutinesCancel): CoroutinesHelper {
        this.coroutinescancel = coroutinesCancel
        return this
    }

    fun checkInternetConnection(value: Boolean): CoroutinesHelper {
        isCheckInternetConnection = value
        return this
    }

    private fun checkInternetConnection(context: Context) {
        val res =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                NetworkUtils.isInternetAvailable(context)
            else
                NetworkUtils.isInternetAvailablePreM(context)

        if (res.not()) throw InternetUnreachableException()
    }

}