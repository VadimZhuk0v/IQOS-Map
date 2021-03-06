package com.vadmax.iqosmap.utils.marker

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.vadmax.iqosmap.data.entities.PointEntity

class MarkerIqos(val pointEntity: PointEntity) : AbstractMarker(pointEntity.lat, pointEntity.lon) {

    init {
        marker = MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(pointEntity.category.iconMap))
    }

    override fun getSnippet() = null

    override fun getTitle() = null
}