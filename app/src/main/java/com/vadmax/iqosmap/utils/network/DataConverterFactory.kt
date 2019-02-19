package com.vadmax.iqosmap.utils.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vadmax.iqosmap.data.entities.PlaceEntity
import com.vadmax.iqosmap.data.entities.PointEntity
import com.vadmax.iqosmap.utils.network.type_adapter.PlaceTypeAdapter
import com.vadmax.iqosmap.utils.network.type_adapter.PointTypeAdapter

object DataConverterFactory {

    val instance: Gson
        get() = GsonBuilder()
            .registerTypeAdapter(PointEntity::class.java, PointTypeAdapter())
            .registerTypeAdapter(PlaceEntity::class.java, PlaceTypeAdapter())
            .setLenient()
            .create()

}