package com.vm.iqosmap.ui.place

import android.app.Application
import com.vm.iqosmap.App
import com.vm.iqosmap.R
import com.vm.iqosmap.base.BaseViewModel
import com.vm.iqosmap.data.entities.PlaceEntity
import com.vm.iqosmap.utils.MLD
import com.vm.iqosmap.utils.coroutines.CoroutinesHelper
import com.vm.iqosmap.view.PlaceHolderView
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