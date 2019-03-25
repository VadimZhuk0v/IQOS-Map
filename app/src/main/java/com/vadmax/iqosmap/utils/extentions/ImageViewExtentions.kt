package com.vadmax.iqosmap.utils.extentions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes


fun ImageView.changeImageAnimated(newImage: Bitmap) {
    val animOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
    val animIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
    animOut.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            this@changeImageAnimated.setImageBitmap(newImage)
            animIn.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
            })
            this@changeImageAnimated.startAnimation(animIn)
        }
    })
    this@changeImageAnimated.startAnimation(animOut)
}

fun ImageView.changeImageAnimated(@DrawableRes newImage: Int, withAnimation: Boolean) =
    if (withAnimation)
        changeImageAnimated(BitmapFactory.decodeResource(context.resources, newImage))
    else
        setImageResource(newImage)