package com.vadmax.iqosmap.ui.map

import com.vadmax.iqosmap.base.BaseRepository
import com.vadmax.iqosmap.data.container.PointQuery

class MapRepository : BaseRepository() {

    fun getPoints(pointQuery: PointQuery) = dataManager.apiHelper.loadPoints(pointQuery)

    val ldSelectedCategories = dataManager.sharedHelper.enabledFiltersLiveData

    val selectedCategories: List<Int>
        get() {
            val result = dataManager.sharedHelper.enabledFilters.toList()
            return if (result.isEmpty())
                listOf(1, 2, 3, 4, 5)
            else
                result
        }

}