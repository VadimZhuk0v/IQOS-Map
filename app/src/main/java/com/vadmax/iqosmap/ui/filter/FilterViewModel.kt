package com.vadmax.iqosmap.ui.filter

import android.app.Application
import com.vadmax.iqosmap.App
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.utils.CategoryEnum
import com.vadmax.iqosmap.utils.MLD
import javax.inject.Inject

class FilterViewModel(app: Application): BaseViewModel(app) {

    @Inject lateinit var repository: FilterRepository

    override fun inject() = App.appComponent.inject(this)

    val ldCategories = MLD<MutableSet<CategoryEnum>>()

    init {
        ldCategories.postValue(repository.enabledFilters)
    }

    fun enableFilters() {
        repository.enabledFilters = ldCategories.value!!
    }

}