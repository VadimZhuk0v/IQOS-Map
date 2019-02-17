package com.vm.iqosmap.ui.main

import android.annotation.SuppressLint
import com.vm.iqosmap.R
import com.vm.iqosmap.base.BaseActivity
import com.vm.iqosmap.databinding.ActivityMainBinding

private const val ACTIVITY_LAYOUT_ID = R.layout.activity_main

@SuppressLint("RestrictedApi")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = ACTIVITY_LAYOUT_ID

    override fun createViewModel() = provideViewModel { MainViewModel(it) }

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

}
