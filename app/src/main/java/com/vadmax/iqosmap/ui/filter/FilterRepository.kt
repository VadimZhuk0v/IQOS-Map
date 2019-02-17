package com.vadmax.iqosmap.ui.filter

import com.vadmax.iqosmap.data.DataManager
import com.vadmax.iqosmap.utils.CategoryEnum
import javax.inject.Inject

class FilterRepository @Inject constructor(private val dataManager: DataManager) {

    var enabledFilters: MutableSet<CategoryEnum>
        get()  {
            val setCategories = mutableSetOf<CategoryEnum>()
            dataManager.sharedHelper.enabledFilters.forEach {
                CategoryEnum.instanceById(it)?.let { categoryEnum -> setCategories.add(categoryEnum) }
            }
            return setCategories
        }
        set(value) {
            dataManager.sharedHelper.enabledFilters = value.map { it.id }.toMutableSet()
        }


}