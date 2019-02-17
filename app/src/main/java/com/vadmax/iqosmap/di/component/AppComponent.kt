package com.vadmax.iqosmap.di.component

import com.vadmax.iqosmap.di.module.AppModule
import com.vadmax.iqosmap.ui.filter.FilterViewModel
import com.vadmax.iqosmap.ui.main.MainViewModel
import com.vadmax.iqosmap.ui.map.MapViewModel
import com.vadmax.iqosmap.ui.place.PlaceViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(viewModel: MainViewModel)
    fun inject(viewModel: MapViewModel)
    fun inject(viewModel: FilterViewModel)
    fun inject(viewModel: PlaceViewModel)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun appModule(appModule: AppModule): Builder
    }
}