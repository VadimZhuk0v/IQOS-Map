package com.vadim.iqosmap.ui.place

import android.app.Application
import com.vadim.iqosmap.App
import com.vadim.iqosmap.BuildConfig
import com.vadim.iqosmap.R
import com.vadim.iqosmap.base.BaseViewModel
import com.vadim.iqosmap.data.entities.PlaceEntity
import com.vadim.iqosmap.utils.MLD
import com.vadim.iqosmap.utils.extentions.applySchedulers
import com.vadim.iqosmap.utils.extentions.checkInternetConnection
import com.vadim.iqosmap.view.PlaceHolderView
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class PlaceViewModel(app: Application, private val placeId: Long) : BaseViewModel(app) {

    @Suppress("ProtectedInFinal")
    @Inject protected lateinit var repository: PlaceRepository

    override fun inject() = App.appComponent.inject(this)

    val ldClose = MLD<Boolean>()
    val ldPlace = MLD<PlaceEntity>()
    var isHolderTimeFinish = false

    private var isLoadingTimeFinish = false

    init {
        showProgress()
        getPlace()
    }

    private fun getPlace() {
        showProgress()

        val disposable = repository.getPlace(placeId)
            .applySchedulers()
            .checkInternetConnection(getApplication())
            .delay(BuildConfig.HOLDER_LOADING_TIME, TimeUnit.MILLISECONDS)
            .subscribe(::onCompleteLoadPlace, ::onError)

        compositeDisposable.add(disposable)
    }

    private fun onCompleteLoadPlace(placeEntity: PlaceEntity) {
        isLoadingTimeFinish = true
        hideProgresMaybe()
        ldPlace.postValue(placeEntity)
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)

        ldClose.postValue(true)
    }

    override fun showProgress() {
        ldHolderState.postValue(PlaceHolderView.HolderState.Progress)
    }

    override fun hideProgress() {
        if (ldHolderState.value!! == PlaceHolderView.HolderState.Progress)
            ldHolderState.postValue(PlaceHolderView.HolderState.Hide)
    }

    override fun showInternetConnectionError() {
        ldHolderState.postValue(PlaceHolderView.HolderState.Error(getApplication<Application>().getString(R.string.error_internet_connection),  needRepeat = true))
    }

    fun hideProgresMaybe() {
        if (isHolderTimeFinish && isLoadingTimeFinish) {
            hideProgress()
        }
    }
}