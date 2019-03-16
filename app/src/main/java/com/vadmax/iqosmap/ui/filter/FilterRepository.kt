package com.vadmax.iqosmap.ui.filter

import com.vadmax.iqosmap.base.BaseRepository
import com.vadmax.iqosmap.data.DataManager
import com.vadmax.iqosmap.utils.CategoryEnum
import javax.inject.Inject

class FilterRepository @Inject constructor(dataManager: DataManager): BaseRepository(dataManager) {

    var enabledFilters: MutableSet<CategoryEnum>
        get()  {
            val setCategories = mutableSetOf<CategoryEnum>()
            dataManager.sharedHelper.enabledFilters.forEach {
                setCategories.add(CategoryEnum.instanceById(it))
            }
            return setCategories
        }
        set(value) {
            dataManager.sharedHelper.enabledFilters = value.map { it.id }.toMutableSet()
        }


}