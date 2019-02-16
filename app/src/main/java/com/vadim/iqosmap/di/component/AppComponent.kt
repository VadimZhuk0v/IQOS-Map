package com.vadim.iqosmap.di.component

import com.vadim.iqosmap.di.module.AppModule
import com.vadim.iqosmap.ui.filter.FilterViewModel
import com.vadim.iqosmap.ui.main.MainViewModel
import com.vadim.iqosmap.ui.map.MapViewModel
import com.vadim.iqosmap.ui.place.PlaceViewModel
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