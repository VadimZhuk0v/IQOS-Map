package com.vm.iqosmap.data.entities

import com.vm.iqosmap.utils.CategoryEnum

data class PointEntity(val id: Long,
                       val lat: Float,
                       val lon: Float,
                       val category: CategoryEnum)