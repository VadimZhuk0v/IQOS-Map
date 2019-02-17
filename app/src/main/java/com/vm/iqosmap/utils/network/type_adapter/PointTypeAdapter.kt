package com.vm.iqosmap.utils.network.type_adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.vm.iqosmap.data.entities.PointEntity
import com.vm.iqosmap.utils.CategoryEnum
import com.vm.iqosmap.utils.network.JsonMissCastException
import java.lang.reflect.Type

class PointTypeAdapter: JsonDeserializer<PointEntity> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): PointEntity {
        if(json == null) throw JsonMissCastException()

        val parentJson = json.asJsonObject

        val id = parentJson.get("id").asLong
        val lat = parentJson.get("lat").asFloat
        val lon = parentJson.get("lon").asFloat
        val categoryId = parentJson.get("category").asInt

        val category = CategoryEnum.instanceById(categoryId)

        return PointEntity(id, lat, lon, category)
    }
}