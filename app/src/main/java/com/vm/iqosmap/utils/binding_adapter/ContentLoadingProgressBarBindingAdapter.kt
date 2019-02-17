package com.vm.iqosmap.utils.binding_adapter

import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.vm.iqosmap.utils.extentions.parenActivity

@BindingAdapter("is_visible")
fun ContentLoadingProgressBar.bindingIsVisible(ld: LiveData<Boolean>) {
    ld.observe(parenActivity ?: return, Observer {
        if (it) this.visibility = View.VISIBLE
        else this.visibility = View.GONE
    })
}