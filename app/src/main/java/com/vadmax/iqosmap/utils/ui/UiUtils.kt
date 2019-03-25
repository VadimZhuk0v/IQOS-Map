package com.vadmax.iqosmap.utils.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.interpolator.view.animation.FastOutSlowInInterpolator


private const val REVEAL_DURATION = 300L

object UiUtils {

    fun hideFragmentWithReveal(
        fragmentManager: FragmentManager?,
        frameLayout: FrameLayout,
        tag: String,
        onFinishEvent: () -> Unit
    ) {
        fragmentManager ?: return

        val cx = frameLayout.width / 2
        val cy = frameLayout.height / 2

        val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(frameLayout, cx, cy, initialRadius, 0f)

        anim.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                frameLayout.visibility = View.INVISIBLE
                val fragment = fragmentManager.findFragmentByTag(tag)
                fragmentManager.beginTransaction()
                    .remove(fragment!!)
                    .commit()

                onFinishEvent()
            }
        })

        anim.duration = REVEAL_DURATION

        anim.start()
    }

    fun startColorAnimation(startColor: Int, endColor: Int, duration: Int) {
        val anim = ValueAnimator()
        anim.setIntValues(startColor, endColor)
        anim.setEvaluator(ArgbEvaluator())
        anim.duration = duration.toLong()
        anim.start()
    }

    fun registerCircularRevealAnimation(
        context: Context,
        view: View,
        startColor: Int,
        endColor: Int
    ) {

        view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                v.removeOnLayoutChangeListener(this)
                val cx = view.width / 2
                val cy = view.height / 2
                val width = view.width
                val height = view.height
                val duration = context.resources.getInteger(android.R.integer.config_mediumAnimTime)


                val finalRadius = Math.sqrt((width * width + height * height).toDouble()).toFloat()
                val anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0f, finalRadius)
                    .setDuration(duration.toLong())
                anim.interpolator = FastOutSlowInInterpolator()
                anim.start()
                startColorAnimation(startColor, endColor, duration)
            }
        })
    }

}