package com.vadim.iqosmap.ui.main

import android.annotation.SuppressLint
import com.vadim.iqosmap.R
import com.vadim.iqosmap.base.BaseActivity
import com.vadim.iqosmap.databinding.ActivityMainBinding

private const val ACTIVITY_LAYOUT_ID = R.layout.activity_main

@SuppressLint("RestrictedApi")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = ACTIVITY_LAYOUT_ID

    override fun createViewModel() = provideViewModel { MainViewModel(it) }

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

}
