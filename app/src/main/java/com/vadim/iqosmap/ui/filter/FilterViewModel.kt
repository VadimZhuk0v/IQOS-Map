package com.vadim.iqosmap.ui.filter

import android.app.Application
import com.vadim.iqosmap.App
import com.vadim.iqosmap.base.BaseViewModel
import com.vadim.iqosmap.utils.CategoryEnum
import com.vadim.iqosmap.utils.MLD
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