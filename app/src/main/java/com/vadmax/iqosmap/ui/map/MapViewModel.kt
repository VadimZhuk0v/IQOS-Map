package com.vadmax.iqosmap.ui.map

import android.app.Application
import com.google.android.gms.maps.model.LatLng
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.data.container.PointQuery
import com.vadmax.iqosmap.data.container.SortedPoints
import com.vadmax.iqosmap.utils.MLD
import com.vadmax.iqosmap.utils.coroutines.CoroutinesHelper
import org.koin.core.inject

class MapViewModel(app: Application) : BaseViewModel<MapRepository>(app) {

    override val repository: MapRepository by inject()

    private val chLoadPoints by lazy { CoroutinesHelper(getApplication()).setCancel(coroutinesRemove) }

    val ldPoints = MLD<SortedPoints>()
    val ldFilters = repository.ldSelectedCategories

    fun loadPoints(center: LatLng, radius: Float) {
        showProgress()

        chLoadPoints.cancel()

        chLoadPoints.launch(::onError) {
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