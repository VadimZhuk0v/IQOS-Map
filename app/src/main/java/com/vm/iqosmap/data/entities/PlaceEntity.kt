package com.vm.iqosmap.data.entities

import com.vm.iqosmap.utils.CategoryEnum

data class PlaceEntity(
    val id: Long,
    val name: String,
    val schedule: String,
    val phone: String,
    val address: String,
    val lat: Float,
    val lon: Float,
    val imageUrl: String,
    val categories: List<CategoryEnum>
)