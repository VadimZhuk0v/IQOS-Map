package com.vadmax.iqosmap.ui.place

import com.vadmax.iqosmap.base.BaseRepository

class PlaceRepository : BaseRepository() {

    fun getPlace(id: Long) = dataManager.apiHelper.loadPlace(id)

}