package com.vadim.iqosmap.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.vadim.iqosmap.BuildConfig
import com.vadim.iqosmap.R
import com.vadim.iqosmap.base.BaseActivity
import com.vadim.iqosmap.databinding.ActivityMainBinding
import com.vadim.iqosmap.ui.filter.FilterFragment
import com.vadim.iqosmap.ui.filter.IFilterCallBack
import com.vadim.iqosmap.ui.map.MapFragment
import com.vadim.iqosmap.ui.place.PlaceBottomSheetDialog
import com.vadim.iqosmap.utils.extentions.addFinishListner
import com.vadim.iqosmap.utils.ui.ConstraintSetUtils
import com.vadim.iqosmap.utils.ui.UiUtils

private const val ACTIVITY_LAYOUT_ID = R.layout.activity_main

@SuppressLint("RestrictedApi")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutId = ACTIVITY_LAYOUT_ID

    override fun createViewModel() = provideViewModel { MainViewModel(it) }

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

}
