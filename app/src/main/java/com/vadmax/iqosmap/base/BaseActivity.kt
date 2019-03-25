package com.vadmax.iqosmap.base

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VM : BaseViewModel<*>, B : ViewDataBinding> : AppCompatActivity() {

    protected abstract val viewModel: VM
    protected lateinit var binding: B
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        binding = DataBindingUtil.setContentView(this, layoutId)
        lifecycle.addObserver(viewModel)
    }

}