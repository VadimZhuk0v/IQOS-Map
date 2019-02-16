package com.vadim.iqosmap.ui.map

import android.app.Application
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.vadim.iqosmap.App
import com.vadim.iqosmap.base.BaseViewModel
import com.vadim.iqosmap.data.container.PointQuery
import com.vadim.iqosmap.data.container.SortedPoints
import com.vadim.iqosmap.data.entities.PointEntity
import com.vadim.iqosmap.utils.MLD
import com.vadim.iqosmap.utils.extentions.checkInternetConnection
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapViewModel(app: Application) : BaseViewModel(app) {

    @Suppress("ProtectedInFinal")
    @Inject protected lateinit var repository: MapRepository

    private var pointsDisposable: Disposable? = null

    val ldPoints = MLD<SortedPoints>()
    val ldFilters = repository.ldSelectedCategories
    var currentMarkers = ArrayList<Marker>()

    override fun inject() = App.appComponent.inject(this)

    fun loadPoints(center: LatLng, radius: Float) {
        showProgress()

        val pointBody = PointQuery(
            center.longitude.toFloat(),
            center.latitude.toFloat(),
            repository.selectedCategories,
            radius.toInt()
        )

        pointsDisposable?.dispose()

        pointsDisposable = repository.getPoints(pointBody)
            .checkInternetConnection(getApplication())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .flatMap(::filterPints)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onCompleteLoadPints, ::onError)

        compositeDisposable.add(pointsDisposable!!)
    }

    private fun onCompleteLoadPints(sortedPoints: SortedPoints) {
        hideProgress()
        ldPoints.postValue(sortedPoints)
    }

    private fun filterPints(points: List<PointEntity>) = Single.just(SortedPoints(points))

}