package com.vadmax.iqosmap.ui.place

import android.app.Application
import com.vadmax.iqosmap.App
import com.vadmax.iqosmap.R
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.data.entities.PlaceEntity
import com.vadmax.iqosmap.utils.MLD
import com.vadmax.iqosmap.utils.coroutines.CoroutinesHelper
import com.vadmax.iqosmap.view.PlaceHolderView
import javax.inject.Inject


class PlaceViewModel(app: Application, private val placeId: Long) : BaseViewModel(app) {

    @Suppress("ProtectedInFinal")
    @Inject protected lateinit var repository: PlaceRepository

    override fun inject() = App.appComponent.inject(this)

    val ldClose = MLD<Boolean>()
    val ldPlace = MLD<PlaceEntity>()

    init {
        showProgress()
        getPlace()
    }

    private fun getPlace() {
        showProgress()

        CoroutinesHelper(getApplication()).setCancel(coroutinesRemove).launch(::onError) {
            val placeEntity = repository.getPlace(placeId).await()
            ldPlace.postValue(placeEntity)
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