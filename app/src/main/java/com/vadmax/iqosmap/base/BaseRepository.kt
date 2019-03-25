package com.vadmax.iqosmap.base

import com.vadmax.iqosmap.data.DataManager
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseRepository: KoinComponent{

    protected val dataManager: DataManager by inject()

}