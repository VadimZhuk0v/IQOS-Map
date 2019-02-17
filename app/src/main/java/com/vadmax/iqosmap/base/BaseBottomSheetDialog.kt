package com.vadmax.iqosmap.base

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vadmax.iqosmap.utils.ViewModelFactory


abstract class BaseBottomSheetDialog<VM : BaseViewModel, B : ViewDataBinding>: BottomSheetDialogFragment() {

    protected val viewModel: VM by lazy { createViewModel() }
    protected lateinit var binding: B
    abstract val layoutId: Int

    abstract fun createViewModel(): VM
    abstract fun setDataToBinding()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        setDataToBinding()

        lifecycle.addObserver(viewModel)
        return binding.root
    }

    protected inline fun <reified VMP : VM> provideViewModel(noinline instance: (application: Application) -> VMP) =
        ViewModelProviders.of(this, ViewModelFactory(activity!!.application, instance)).get(VMP::class.java)




}