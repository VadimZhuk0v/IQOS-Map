package com.vm.iqosmap.ui.filter

import android.app.Application
import com.vm.iqosmap.App
import com.vm.iqosmap.base.BaseViewModel
import com.vm.iqosmap.utils.CategoryEnum
import com.vm.iqosmap.utils.MLD
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