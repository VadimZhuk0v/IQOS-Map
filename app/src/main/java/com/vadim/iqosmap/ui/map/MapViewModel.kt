package com.vadim.iqosmap.ui.map

import android.app.Application
import com.google.android.gms.maps.model.LatLng
import com.vadim.iqosmap.App
import com.vadim.iqosmap.base.BaseViewModel
import com.vadim.iqosmap.data.container.PointQuery
import com.vadim.iqosmap.data.container.SortedPoints
import com.vadim.iqosmap.utils.MLD
import com.vadim.iqosmap.utils.coroutines.CoroutinesHelper
import javax.inject.Inject

open class MapViewModel(app: Application) : BaseViewModel(app) {

    @Inject protected lateinit var repository: MapRepository

    private val pointsJob by lazy { CoroutinesHelper(getApplication()).setCancel(coroutinesRemove) }

    val ldPoints = MLD<SortedPoints>()
    val ldFilters = repository.ldSelectedCategories

    override fun inject() = App.appComponent.inject(this)

    fun loadPoints(center: LatLng, radius: Float) {
        showProgress()

        val pointBody = PointQuery(
            center.longitude.toFloat(),
            center.latitude.toFloat(),
            repository.selectedCategories,
            radius.toInt()
        )

        pointsJob.cancel()

        pointsJob.launch(::onError) {
            val points = SortedPoints(repository.getPoints(pointBody).await())
            ldPoints.postValue(points)
            hideProgress()
        }

    }

}