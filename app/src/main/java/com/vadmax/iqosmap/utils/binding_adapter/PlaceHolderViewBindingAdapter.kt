package com.vadmax.iqosmap.utils.binding_adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.vadmax.iqosmap.view.PlaceHolderView

@BindingAdapter("dataContainer")
fun PlaceHolderView.bindDataContainer(view: View?) {
    view?.let { this.setDataContainer(it) }
}

@BindingAdapter("state")
fun PlaceHolderView.bindProgress(state: PlaceHolderView.HolderState?) {
    state ?: return

    when (state) {
        is PlaceHolderView.HolderState.Progress -> showProgress()
        is PlaceHolderView.HolderState.Hide -> hideProgress()
        is PlaceHolderView.HolderState.Error -> showError(state.message, state.imgRes, state.needRepeat)
    }
}