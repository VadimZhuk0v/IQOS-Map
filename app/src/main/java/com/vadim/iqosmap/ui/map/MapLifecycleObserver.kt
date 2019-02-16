package com.vadim.iqosmap.ui.map

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.MapView

class MapLifecycleObserver(private val mapView: MapView,
                           private val fusedLocationProviderClient: FusedLocationProviderClient,
                           private val locationCallback: LocationCallback): LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        mapView.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(){
        mapView.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        mapView.onDestroy()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}