package com.vadmax.iqosmap.ui.place

import com.vadmax.iqosmap.base.BaseRepository
import com.vadmax.iqosmap.data.DataManager
import javax.inject.Inject

class PlaceRepository @Inject constructor(dataManager: DataManager): BaseRepository(dataManager) {

    fun getPlace(id: Long) = dataManager.apiHelper.loadPlace(id)

}