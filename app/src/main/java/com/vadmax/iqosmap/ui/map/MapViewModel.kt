package com.vadmax.iqosmap.ui.map

import android.app.Application
import com.google.android.gms.maps.model.LatLng
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.data.container.PointQuery
import com.vadmax.iqosmap.data.container.SortedPoints
import com.vadmax.iqosmap.utils.MLD
import kotlinx.coroutines.Job
import org.koin.core.inject

class MapViewModel(app: Application) : BaseViewModel<MapRepository>(app) {

    override val repository: MapRepository by inject()

    private var jobPoints: Job? = null

    val ldPoints = MLD<SortedPoints>()
    val ldFilters = repository.ldSelectedCategories

    fun loadPoints(center: LatLng, radius: Float) {
        showProgress()

        jobPoints?.cancel()

        jobPoints = launch(::onError) {
            val pointBody = PointQuery(
                center.longitude.toFloat(),
                center.latitude.toFloat(),
                repository.selectedCategories,
                radius.toInt()
            )
            val points = SortedPoints(repository.getPoints(pointBody))
            ldPoints.postValue(points)
            hideProgress()
        }

    }

}