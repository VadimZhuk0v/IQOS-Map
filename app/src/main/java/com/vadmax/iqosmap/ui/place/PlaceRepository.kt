package com.vadmax.iqosmap.ui.place

import com.vadmax.iqosmap.data.DataManager
import javax.inject.Inject

class PlaceRepository @Inject constructor(private val dataManager: DataManager) {

    fun getPlace(id: Long) = dataManager.apiHelper.loadPlace(id)

}