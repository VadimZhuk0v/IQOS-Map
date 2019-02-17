package com.vm.iqosmap.di.component

import com.vm.iqosmap.di.module.AppModule
import com.vm.iqosmap.ui.filter.FilterViewModel
import com.vm.iqosmap.ui.main.MainViewModel
import com.vm.iqosmap.ui.map.MapViewModel
import com.vm.iqosmap.ui.place.PlaceViewModel
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