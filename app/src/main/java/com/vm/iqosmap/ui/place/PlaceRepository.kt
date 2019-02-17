package com.vm.iqosmap.ui.place

import com.vm.iqosmap.data.DataManager
import javax.inject.Inject

class PlaceRepository @Inject constructor(private val dataManager: DataManager) {

    fun getPlace(id: Long) = dataManager.apiHelper.loadPlace(id)

}