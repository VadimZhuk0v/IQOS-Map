package com.vm.iqosmap.utils.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vm.iqosmap.data.entities.PlaceEntity
import com.vm.iqosmap.data.entities.PointEntity
import com.vm.iqosmap.utils.network.type_adapter.PlaceTypeAdapter
import com.vm.iqosmap.utils.network.type_adapter.PointTypeAdapter

object DataConverterFactory {

    val instance: Gson
        get() = GsonBuilder()
            .registerTypeAdapter(PointEntity::class.java, PointTypeAdapter())
            .registerTypeAdapter(PlaceEntity::class.java, PlaceTypeAdapter())
            .setLenient()
            .create()

}