package com.vadmax.iqosmap.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import com.vadmax.iqosmap.R
import com.vadmax.iqosmap.base.BaseActivity
import com.vadmax.iqosmap.databinding.ActivityMainBinding
import com.vadmax.iqosmap.ui.filter.FilterFragment
import com.vadmax.iqosmap.ui.filter.IFilterCallBack
import com.vadmax.iqosmap.utils.extentions.setupWithNavController
import com.vadmax.iqosmap.utils.ui.UiUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ACTIVITY_LAYOUT_ID = R.layout.activity_main

@SuppressLint("RestrictedApi")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), IFilterCallBack {

    override val layoutId = ACTIVITY_LAYOUT_ID

    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        binding.viewModel = viewModel

    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.navigation_map, R.navigation.navigation_news)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.navHostContainer,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer {
            //   setupActionBarWithNavController(navController)
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
    }

    private fun showFilterFragment() {

        TransitionManager.beginDelayedTransition(binding.clRoot, transition)

        cs.applyTo(binding.clRoot)
    }

    override fun hideFilterFragment() {
//        val onFinishEvent = {
//            binding.fabFilter.visibility = View.VISIBLE
//
//            val margin = resources.getDimensionPixelOffset(R.dimen.double_margin)
//            val cs = ConstraintSet().apply { clone(binding.clRoot) }
//
//            ConstraintSetUtils.bottomLeftView(R.id.fabFilter, cs, margin, margin)
//
//            TransitionManager.beginDelayedTransition(binding.clRoot)
//
//            cs.applyTo(binding.clRoot)
//        }
//
        UiUtils.hideFragmentWithReveal(childFragmentManager, binding.flFilter, FilterFragment.TAG, onFinishEvent)
   }

}
