package com.vadim.iqosmap.utils.extentions

import android.graphics.Bitmap
import android.location.Location
import android.os.Handler
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.vadim.iqosmap.R


fun GoogleMap.addMarkerWithAnimation(icon: Bitmap, markerOptions: MarkerOptions, duration: Long = 100): Marker {
    val marker = this.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_empty)))
    val handler = Handler()
    val startTime = System.currentTimeMillis()

    handler.post(object : Runnable {
        override fun run() {
            val elapsed = System.currentTimeMillis() - startTime
            val t = elapsed.toFloat() / duration
            if (t <= 1) {
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon.scaleBitmap(t)))
                handler.postDelayed(this, 16)
            } else marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon))
        }
    })

    return marker
}

fun GoogleMap.removeMarkerWithAnimation(marker: Marker, icon: Bitmap, duration: Long = 100) {
    val handler = Handler()
    val startTime = System.currentTimeMillis()

    handler.post(object : Runnable {
        override fun run() {
            val elapsed = System.currentTimeMillis() - startTime
            val t = elapsed.toFloat() / duration
            if (t < 1 && t > 0) {
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon.scaleBitmap(1 - t)))
                handler.postDelayed(this, 16)
            } else marker.remove()
        }
    })
}

val GoogleMap.radius: Float
get() {
    val visibleRegion = this.projection.visibleRegion

    val farRight = visibleRegion.farRight
    val farLeft = visibleRegion.farLeft
    val nearRight = visibleRegion.nearRight
    val nearLeft = visibleRegion.nearLeft

    val distanceWidth = FloatArray(2)
    Location.distanceBetween(
        (farRight.latitude + nearRight.latitude) / 2,
        (farRight.longitude + nearRight.longitude) / 2,
        (farLeft.latitude + nearLeft.latitude) / 2,
        (farLeft.longitude + nearLeft.longitude) / 2,
        distanceWidth
    )


    val distanceHeight = FloatArray(2)
    Location.distanceBetween(
        (farRight.latitude + nearRight.latitude) / 2,
        (farRight.longitude + nearRight.longitude) / 2,
        (farLeft.latitude + nearLeft.latitude) / 2,
        (farLeft.longitude + nearLeft.longitude) / 2,
        distanceHeight
    )

    return if (distanceWidth[0] > distanceHeight[0]) {
        distanceWidth[0]
    } else {
        distanceHeight[0]
    }
}