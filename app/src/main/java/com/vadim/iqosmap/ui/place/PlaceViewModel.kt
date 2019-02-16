package com.vadim.iqosmap.ui.place

import android.app.Application
import com.vadim.iqosmap.App
import com.vadim.iqosmap.R
import com.vadim.iqosmap.base.BaseViewModel
import com.vadim.iqosmap.data.entities.PlaceEntity
import com.vadim.iqosmap.utils.MLD
import com.vadim.iqosmap.utils.coroutines.CoroutinesHelper
import com.vadim.iqosmap.view.PlaceHolderView
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