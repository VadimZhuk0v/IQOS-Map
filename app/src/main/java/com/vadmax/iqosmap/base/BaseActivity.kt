package com.vadmax.iqosmap.base

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.vadmax.iqosmap.utils.ViewModelFactory

abstract class BaseActivity<VM : BaseViewModel<*>, B : ViewDataBinding> : AppCompatActivity() {

    val viewModel: VM by lazy { createViewModel() }
    protected lateinit var binding: B
    abstract val layoutId: Int

    abstract fun createViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        binding = DataBindingUtil.setContentView(this, layoutId)
        lifecycle.addObserver(viewModel)
        setDataToBinding()
    }

    open fun setDataToBinding() = Unit

    protected inline fun <reified VMP : VM> provideViewModel(noinline instance: (application: Application) -> VMP) =
        ViewModelProviders.of(this, ViewModelFactory(application, instance)).get(VMP::class.java)

}