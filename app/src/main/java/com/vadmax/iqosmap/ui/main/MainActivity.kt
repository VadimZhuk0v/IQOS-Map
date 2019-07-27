package com.vadmax.iqosmap.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import com.vadmax.iqosmap.R
import com.vadmax.iqosmap.base.BaseActivity
import com.vadmax.iqosmap.databinding.ActivityMainBinding
import com.vadmax.iqosmap.utils.extentions.observe
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ACTIVITY_LAYOUT_ID = R.layout.activity_main

@SuppressLint("RestrictedApi")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = ACTIVITY_LAYOUT_ID

    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        observeAds()
    }

    private fun observeAds() {
        viewModel.ldAdRequest.observe(this) {
            binding.advHeader.loadAd(it)
        }
    }

}
