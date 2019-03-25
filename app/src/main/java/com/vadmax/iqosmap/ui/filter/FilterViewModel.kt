package com.vadmax.iqosmap.ui.filter

import android.app.Application
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.utils.CategoryEnum
import com.vadmax.iqosmap.utils.MLD
import org.koin.core.inject

class FilterViewModel(app: Application): BaseViewModel<FilterRepository>(app) {

    override val repository: FilterRepository by inject()

    val ldCategories = MLD<MutableSet<CategoryEnum>>()

    init {
        ldCategories.postValue(repository.enabledFilters)
    }

    fun enableFilters() {
        repository.enabledFilters = ldCategories.value!!
    }

}