package com.vadmax.iqosmap.utils.binding_adapter

import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("is_visible")
fun ContentLoadingProgressBar.bindingIsVisible(state: Boolean?) {
    state ?: return

    if (state)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}