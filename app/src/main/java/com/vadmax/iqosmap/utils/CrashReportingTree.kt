package com.vadmax.iqosmap.utils

import timber.log.Timber

class CrashReportingTree: Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        
    }
}