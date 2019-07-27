package com.vadmax.iqosmap.data.api

import com.vadmax.iqosmap.data.container.PointQuery

class ApiHelper(private val apiInterface: ApiInterface) {

    suspend fun loadPoints(pointQuery: PointQuery) =
        apiInterface.loadPints(pointQuery.asQueryMap)

    suspend fun loadPlace(id: Long) = apiInterface.loadPlace(id)

}