package com.vadmax.iqosmap.data.container

import com.vadmax.iqosmap.BuildConfig

data class PointQuery(
    val lon: Float,
    val lat: Float,
    val categories: List<Int>,
    val radius: Int
) {
    val asQueryMap
        get() = mapOf(
            "lon" to lon.toString(),
            "lat" to lat.toString(),
            "categories" to categories.joinToString().replace(", ", "|"),
            "radius" to radius.toString(),
            "count" to  BuildConfig.DOWNLOADING_POINTS_LIMIT.toString()
        )
}