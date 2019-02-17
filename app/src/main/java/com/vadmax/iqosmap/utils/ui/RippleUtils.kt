package com.vadmax.iqosmap.utils.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable


object RippleUtils {

    fun getBackgroundDrawable(pressedColor: Int): RippleDrawable {
        return RippleDrawable(getPressedState(pressedColor), ColorDrawable(Color.WHITE), null)
    }

    private fun getPressedState(pressedColor: Int): ColorStateList {
        return ColorStateList(arrayOf(intArrayOf()), intArrayOf(pressedColor))
    }

}
