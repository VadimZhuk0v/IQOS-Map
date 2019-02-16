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
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), IFilterCallBack {

    override val layoutId = ACTIVITY_LAYOUT_ID

    override fun createViewModel() = provideViewModel { MainViewModel(it) }

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListeners()
        setMapObserver()
    }

    private fun setMapObserver() {
        (supportFragmentManager.findFragmentById(R.id.fragMap) as MapFragment).setMapObserver(Observer {
            PlaceBottomSheetDialog.newInstance(it ?: return@Observer).show(supportFragmentManager, null)
        })
    }

    private fun setListeners() {
        binding.fabFilter.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentByTag(FilterFragment.TAG)
            if (fragment == null)
                showFilterFragment()
            else
                hideFilterFragment()
        }

        binding.fabPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${BuildConfig.IQOS_PHONE}")
            startActivity(intent)
        }

        binding.fabWeb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(BuildConfig.IQOS_SITE)
            startActivity(intent)
        }
    }


    private fun showFilterFragment() {
        val cs = ConstraintSet().apply { clone(binding.clRoot) }

        ConstraintSetUtils.centerView(R.id.fabFilter, cs)

        val transition = ChangeBounds()
        transition.addFinishListner {
            binding.flFilter.visibility = View.VISIBLE

            val filterFragment = FilterFragment.newInstance()

            supportFragmentManager.beginTransaction()
                .add(R.id.flFilter, filterFragment, FilterFragment.TAG)
                .commit()

            binding.fabFilter.visibility = View.GONE
        }

        TransitionManager.beginDelayedTransition(binding.clRoot, transition)

        cs.applyTo(binding.clRoot)
    }

    override fun hideFilterFragment() {
        val onFinishEvent = {
            binding.fabFilter.visibility = View.VISIBLE

            val margin = resources.getDimensionPixelOffset(R.dimen.double_margin)
            val cs = ConstraintSet().apply { clone(binding.clRoot) }

            ConstraintSetUtils.bottomLeftView(R.id.fabFilter, cs, margin, margin)

            TransitionManager.beginDelayedTransition(binding.clRoot)

            cs.applyTo(binding.clRoot)
        }

        UiUtils.hideFragmentWithReveal(supportFragmentManager, binding.flFilter, FilterFragment.TAG, onFinishEvent)
    }
}
