package com.vadmax.iqosmap.ui.filter

import com.vadmax.iqosmap.base.BaseRepository
import com.vadmax.iqosmap.utils.CategoryEnum

class FilterRepository : BaseRepository() {

    var enabledFilters: MutableSet<CategoryEnum>
        get() {
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