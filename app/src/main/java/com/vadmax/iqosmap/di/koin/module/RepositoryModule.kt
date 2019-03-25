package com.vadmax.iqosmap.di.koin.module

import com.vadmax.iqosmap.ui.filter.FilterRepository
import com.vadmax.iqosmap.ui.main.MainRepository
import com.vadmax.iqosmap.ui.map.MapRepository
import com.vadmax.iqosmap.ui.place.PlaceRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MainRepository() }
    single { PlaceRepository() }
    single { FilterRepository() }
    single { MapRepository() }
}