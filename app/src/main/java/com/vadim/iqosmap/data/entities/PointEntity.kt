package com.vadim.iqosmap.data.entities

import com.vadim.iqosmap.utils.CategoryEnum

data class PointEntity(val id: Long,
                       val lat: Float,
                       val lon: Float,
                       val category: CategoryEnum)