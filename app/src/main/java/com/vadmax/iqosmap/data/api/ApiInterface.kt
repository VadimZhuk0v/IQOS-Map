package com.vadmax.iqosmap.data.api

import com.vadmax.iqosmap.data.entities.PlaceEntity
import com.vadmax.iqosmap.data.entities.PointEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("api/points")
    suspend fun loadPints(@QueryMap pintQuery: Map<String, String>): List<PointEntity>

    @GET("api/point/{id}")
    suspend fun loadPlace(@Path("id") id: Long): PlaceEntity

}