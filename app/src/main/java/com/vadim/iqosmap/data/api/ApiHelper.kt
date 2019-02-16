package com.vadim.iqosmap.data.api

import com.vadim.iqosmap.data.container.PointQuery

class ApiHelper(private val apiInterface: ApiInterface) {

    fun loadPoints(pointQuery: PointQuery) =
        apiInterface.loadPints(pointQuery.asQueryMap)

    fun loadPlace(id: Long) = apiInterface.loadPlace(id)

}