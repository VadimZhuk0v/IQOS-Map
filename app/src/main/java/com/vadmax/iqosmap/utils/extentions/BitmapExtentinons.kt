package com.vadmax.iqosmap.utils.extentions

import android.graphics.Bitmap

fun Bitmap.scaleBitmap(scaleFactor: Float): Bitmap {
    val sizeX = Math.round(this.width * scaleFactor)
    val sizeY = Math.round(this.height * scaleFactor)
    return Bitmap.createScaledBitmap(this, sizeX, sizeY, false)
}