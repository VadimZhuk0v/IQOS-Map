package com.vadmax.iqosmap.utils.coroutines

import android.content.Context
import android.os.Build
import com.vadmax.iqosmap.BuildConfig
import com.vadmax.iqosmap.utils.network.InternetUnreachableException
import com.vadmax.iqosmap.utils.network.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CoroutinesHelper(private val context: Context, private var scope: CoroutineScope) {

    fun launch(
        onError: (e: Throwable) -> Unit = ::defaultOnError,
        checkInternetConnection: Boolean = true,
        coroutineContext: CoroutineContext = Dispatchers.IO,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch(coroutineContext) {
            try {
                if (checkInternetConnection)
                    checkInternetConnection(context)

                block()
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) { onError(e) }
            }
        }
    }

    fun timer(
        seconds: Int,
        onError: (e: Exception) -> Unit = ::defaultOnError,
        block: suspend CoroutineScope.(timerResponse: TimerResponse) -> Unit
    ): Job {
        return scope.launch(Dispatchers.IO) {
            try {
                repeat(seconds + 1) {
                    delay(1000)
                    block(TimerResponse((seconds - it).toLong(), seconds.toLong(), it >= seconds))
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onError(e) }
            }
        }
    }

    private fun defaultOnError(e: Throwable) {
        if(BuildConfig.DEBUG)
            e.printStackTrace()
    }

    private fun checkInternetConnection(context: Context) {
        val res = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkUtils.isInternetAvailable(context)
        } else {
            NetworkUtils.isInternetAvailablePreM(context)
        }

        if (res.not()) throw InternetUnreachableException()
    }

    inner class TimerResponse constructor(val timeLeft: Long, val totalTime: Long, val isFinished: Boolean)

}