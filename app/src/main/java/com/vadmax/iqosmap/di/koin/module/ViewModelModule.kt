package com.vadmax.iqosmap.di.koin.module

import com.vadmax.iqosmap.ui.filter.FilterViewModel
import com.vadmax.iqosmap.ui.main.MainViewModel
import com.vadmax.iqosmap.ui.map.MapViewModel
import com.vadmax.iqosmap.ui.place.PlaceViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { (placeId: Long) -> PlaceViewModel(get(), placeId) }
    viewModel { FilterViewModel(get()) }
    viewModel { MapViewModel(get()) }
}