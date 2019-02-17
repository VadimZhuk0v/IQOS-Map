package com.vadmax.iqosmap.utils.extentions

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity


val View.relativeLeft: Int
    get() {
        return if (this.parent === this.rootView)
            this.left
        else
            this.left + (parent as View).relativeLeft
    }

val View.relativeTop: Int
    get() {
        return if (this.parent === this.rootView)
            this.top
        else
            this.top+ (parent as View).relativeTop
    }

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