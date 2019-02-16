package com.vadim.iqosmap.ui.place

import com.vadim.iqosmap.data.DataManager
import com.vadim.iqosmap.data.entities.PlaceEntity
import com.vadim.iqosmap.utils.CategoryEnum
import io.reactivex.Single
import javax.inject.Inject

class PlaceRepository @Inject constructor(private val dataManager: DataManager) {

    fun getPlace(id: Long) = dataManager.apiHelper.loadPlace(id)

}