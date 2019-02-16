package com.vadim.iqosmap.base

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vadim.iqosmap.utils.ViewModelFactory

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding> : Fragment() {

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