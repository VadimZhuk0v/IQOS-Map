package com.vadmax.iqosmap.ui.filter

import android.app.Application
import com.vadmax.iqosmap.App
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.utils.CategoryEnum
import com.vadmax.iqosmap.utils.MLD

class FilterViewModel(app: Application): BaseViewModel<FilterRepository>(app) {

    override fun inject() = App.appComponent.inject(this)

    val ldCategories = MLD<MutableSet<CategoryEnum>>()

    init {
        ldCategories.postValue(repository.enabledFilters)
    }

    fun enableFilters() {
        repository.enabledFilters = ldCategories.value!!
    }

}