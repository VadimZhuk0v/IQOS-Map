package com.vadmax.iqosmap.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog<VM : BaseViewModel<*>, B : ViewDataBinding> : BottomSheetDialogFragment() {

    protected abstract val viewModel: VM
    protected lateinit var binding: B
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        lifecycle.addObserver(viewModel)
        return binding.root
    }

}