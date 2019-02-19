package com.vadmax.iqosmap.utils.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import com.vadmax.iqosmap.utils.MLD
import com.vadmax.iqosmap.utils.extentions.parenActivity
import com.vadmax.iqosmap.view.PlaceHolderView

@BindingAdapter("dataContainer")
fun PlaceHolderView.bindDataContainer(view: View?) {
    view?.let { this.setDataContainer(it) }
}

@BindingAdapter("state")
fun PlaceHolderView.bindProgress(state: MLD<PlaceHolderView.HolderState>?) {
    state ?: return
    val activity = parenActivity ?: return

    state.observe(activity, Observer {
        it ?: return@Observer

        when(it){
            is PlaceHolderView.HolderState.Progress -> showProgress()
            is PlaceHolderView.HolderState.Hide -> hideProgress()
            is PlaceHolderView.HolderState.Error -> showError(it.message, it.imgRes, it.needRepeat)
        }
    })
}