package com.vadmax.iqosmap.data.entities

import com.vadmax.iqosmap.utils.CategoryEnum

data class PointEntity(val id: Long,
                       val lat: Float,
                       val lon: Float,
                       val category: CategoryEnum)