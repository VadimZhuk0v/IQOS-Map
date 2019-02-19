package com.vadmax.iqosmap.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.lifecycle.Observer
import com.vadmax.iqosmap.R
import com.vadmax.iqosmap.base.BaseActivity
import com.vadmax.iqosmap.databinding.ActivityMainBinding

private const val ACTIVITY_LAYOUT_ID = R.layout.activity_main

@SuppressLint("RestrictedApi")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = ACTIVITY_LAYOUT_ID

    override fun createViewModel() = provideViewModel { MainViewModel(it) }

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observerServerVersion()
    }

    private fun observerServerVersion() {
        viewModel.ldServerVersion.observe(this, Observer { showUpdateAlert() })
    }

    private fun showUpdateAlert() {
        AlertDialog.Builder(this)
            .setTitle(R.string.attention)
            .setMessage(R.string.new_version_is_available)
            .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
            .show()
    }

}
