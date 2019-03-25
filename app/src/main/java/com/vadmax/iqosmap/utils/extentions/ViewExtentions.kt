package com.vadmax.iqosmap.utils.extentions

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

val View.parenActivity: AppCompatActivity?
    get() {
        var context = this.context
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }