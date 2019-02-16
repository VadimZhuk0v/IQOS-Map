package com.vadim.iqosmap.utils.extentions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes


fun ImageView.cheangeImageAnimated(newImage: Bitmap) {
    val anim_out = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
    val anim_in = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
    anim_out.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationRepeat(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            this@cheangeImageAnimated.setImageBitmap(newImage)
            anim_in.setAnimationListener(object : AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
            })
            this@cheangeImageAnimated.startAnimation(anim_in)
        }
    })
    this@cheangeImageAnimated.startAnimation(anim_out)
}

fun ImageView.cheangeImageAnimated(@DrawableRes newImage: Int, withAnimation: Boolean) =
    if (withAnimation)
        cheangeImageAnimated(BitmapFactory.decodeResource(context.resources, newImage))
    else
        setImageResource(newImage)