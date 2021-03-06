package com.vadmax.iqosmap.ui.place

import android.app.Application
import com.vadmax.iqosmap.R
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.data.entities.PlaceEntity
import com.vadmax.iqosmap.utils.MLD
import com.vadmax.iqosmap.view.PlaceHolderView
import org.koin.core.inject

class PlaceViewModel(app: Application, private val placeId: Long) : BaseViewModel<PlaceRepository>(app) {

    override val repository: PlaceRepository by inject()

    val ldClose = MLD<Boolean>()
    val ldPlace = MLD<PlaceEntity>()

    init {
        showProgress()
        getPlace()
    }

    private fun getPlace() {
        showProgress()

        launch(::onError) {
            ldPlace.postValue(repository.getPlace(placeId))
            hideProgress()
        }
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)

        ldClose.postValue(true)
    }

    override fun showInternetConnectionError() {
        ldHolderState.postValue(
            PlaceHolderView.HolderState.Error(
                getApplication<Application>().getString(R.string.error_internet_connection),
                needRepeat = true
            )
        )
    }

    override fun showProgress() {
        ldHolderState.postValue(PlaceHolderView.HolderState.Progress)
    }

    override fun hideProgress() {
        ldHolderState.postValue(PlaceHolderView.HolderState.Hide)
    }

}