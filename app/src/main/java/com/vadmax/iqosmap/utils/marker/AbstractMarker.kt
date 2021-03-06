package com.vadmax.iqosmap.utils.marker

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterItem

abstract class AbstractMarker(
    private var latitude: Float,
    private var longitude: Float
) : ClusterItem {

    override fun getPosition() = LatLng(latitude.toDouble(), longitude.toDouble())

    protected lateinit var marker: MarkerOptions

}