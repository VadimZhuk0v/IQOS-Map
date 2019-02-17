package com.vm.iqosmap.utils.network.type_adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.vm.iqosmap.data.entities.PlaceEntity
import com.vm.iqosmap.utils.CategoryEnum
import com.vm.iqosmap.utils.network.JsonMissCastException
import java.lang.reflect.Type

class PlaceTypeAdapter : JsonDeserializer<PlaceEntity> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): PlaceEntity {
        if (json == null) throw JsonMissCastException()

        val parentJson = json.asJsonObject

        val id = parentJson.get("id").asLong
        val lat = parentJson.get("latitude").asFloat
        val lon = parentJson.get("longitude").asFloat
        val name = parentJson.get("name").asString
        val address = parentJson.get("address").asString
        val phone = parentJson.get("phone").asString
        val schedule = parentJson.get("schedule").asString
        val imageUrl = parentJson.get("url").asString

        val categories = ArrayList<CategoryEnum>()
        parentJson.get("categories").asJsonArray.forEach {
            val categoryEnum = CategoryEnum.instanceById(it.asInt)
            if (categoryEnum != CategoryEnum.BASE)
                categories.add(categoryEnum)
        }

        return PlaceEntity(id,name, schedule, phone, address, lat, lon, imageUrl, categories)
    }
}