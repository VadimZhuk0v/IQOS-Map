package com.vm.iqosmap.data.api

import com.vm.iqosmap.data.entities.PlaceEntity
import com.vm.iqosmap.data.entities.PointEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("api/points")
    fun loadPints(@QueryMap pintQuery: Map<String, String>): Deferred<List<PointEntity>>

    @GET("api/point/{id}")
    fun loadPlace(@Path("id") id: Long): Deferred<PlaceEntity>

}