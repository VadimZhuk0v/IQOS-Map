package com.vm.iqosmap.ui.map

import com.vm.iqosmap.data.DataManager
import com.vm.iqosmap.data.container.PointQuery
import javax.inject.Inject

class MapRepository @Inject constructor(private val dataManager: DataManager) {

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