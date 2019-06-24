package com.vadmax.iqosmap.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.github.florent37.runtimepermission.RuntimePermission
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.maps.android.clustering.ClusterManager
import com.vadmax.iqosmap.R
import com.vadmax.iqosmap.base.BaseFragment
import com.vadmax.iqosmap.databinding.FragmentMapBinding
import com.vadmax.iqosmap.ui.place.PlaceBottomSheetDialog
import com.vadmax.iqosmap.utils.extentions.radius
import com.vadmax.iqosmap.utils.extentions.toLatLng
import com.vadmax.iqosmap.utils.marker.FilteredClusterManager
import com.vadmax.iqosmap.utils.marker.MarkerIqos
import com.vadmax.iqosmap.utils.marker.OwnIconRender
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val FRAGMENT_LAYOUT_ID = R.layout.fragment_map
private const val REQUEST_CHECK_SETTINGS = 1111
private const val MARKER_ZOOM = 16.0f

@SuppressLint("RestrictedApi")
class MapFragment : BaseFragment<MapViewModel, FragmentMapBinding>(), OnMapReadyCallback {

    override val layoutId = FRAGMENT_LAYOUT_ID

    override val viewModel: MapViewModel by viewModel()

    private lateinit var cmSticks: FilteredClusterManager
    private lateinit var cmDevices: FilteredClusterManager
    private lateinit var cmAccessories: FilteredClusterManager
    private lateinit var cmService: FilteredClusterManager
    private lateinit var cmFriendly: FilteredClusterManager
    private lateinit var cmBase: FilteredClusterManager

    private var googleMap: GoogleMap? = null
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(activity!!) }

    private val locationRequest = LocationRequest().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    private val locationCallback = object : LocationCallback() {

    }

    override fun onLowMemory() {
        super.onLowMemory()

        binding.map.onLowMemory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        lifecycle.addObserver(MapLifecycleObserver(binding.map, fusedLocationClient, locationCallback))
        initMap(savedInstanceState)
    }

    override fun onMapReady(map: GoogleMap) {
        this.googleMap = map

        initClusterManagers()
        enableLocation()
        serClustersRender()
        setClusterManagersListeners()

        googleMap?.setOnCameraIdleListener {
            viewModel.loadPoints(googleMap!!.cameraPosition.target, googleMap!!.radius)
            cmSticks.onCameraIdle()
            cmDevices.onCameraIdle()
            cmAccessories.onCameraIdle()
            cmService.onCameraIdle()
            cmFriendly.onCameraIdle()
            cmBase.onCameraIdle()
        }

        observePoints()
        observeFilters()
    }

    private fun observeFilters() {
        viewModel.ldFilters.observe(this, Observer {
            googleMap?.let { map ->
                viewModel.loadPoints(map.cameraPosition.target, map.radius)
            }
        })
    }

    private fun observePoints() {
        viewModel.ldPoints.observe(this, Observer {
            cmSticks.addItems(it.listSticks)
            cmSticks.cluster()
            cmDevices.addItems(it.listDevices)
            cmDevices.cluster()
            cmAccessories.addItems(it.listAccessories)
            cmAccessories.cluster()
            cmService.addItems(it.listService)
            cmService.cluster()
            cmFriendly.addItems(it.listFriendly)
            cmFriendly.cluster()
            cmBase.addItems(it.listBase)
            cmBase.cluster()
        })
    }

    private fun enableLocation() {
        RuntimePermission.askPermission(this, Manifest.permission.ACCESS_FINE_LOCATION).ask {
            onLocationPermission(it.isAccepted)
        }
    }

    @SuppressLint("MissingPermission")
    private fun onLocationPermission(isGranted: Boolean) {
        if (isGranted.not()) return
        checkLocationSettings()

        googleMap?.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it ?: return@addOnSuccessListener
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(it.toLatLng(), MARKER_ZOOM))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            startLocationUpdates()
        }
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        googleMap?.isMyLocationEnabled = true
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun checkLocationSettings() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val task = LocationServices.getSettingsClient(activity!!).checkLocationSettings(builder.build())
        task.addOnSuccessListener { startLocationUpdates() }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(activity!!, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }


    private fun initMap(bundle: Bundle?) {
        binding.map.onCreate(bundle)
        binding.map.getMapAsync(this)
    }

    private fun serClustersRender() {
        cmSticks.renderer = OwnIconRender(context!!, googleMap!!, cmSticks)
        cmDevices.renderer = OwnIconRender(context!!, googleMap!!, cmDevices)
        cmAccessories.renderer = OwnIconRender(context!!, googleMap!!, cmAccessories)
        cmService.renderer = OwnIconRender(context!!, googleMap!!, cmService)
        cmFriendly.renderer = OwnIconRender(context!!, googleMap!!, cmFriendly)
        cmBase.renderer = OwnIconRender(context!!, googleMap!!, cmBase)
    }

    private fun initClusterManagers() {
        cmSticks = FilteredClusterManager(context!!, googleMap!!)
        cmDevices = FilteredClusterManager(context!!, googleMap!!)
        cmAccessories = FilteredClusterManager(context!!, googleMap!!)
        cmService = FilteredClusterManager(context!!, googleMap!!)
        cmFriendly = FilteredClusterManager(context!!, googleMap!!)
        cmBase = FilteredClusterManager(context!!, googleMap!!)
    }

    private fun setClusterManagersListeners() {
        val listener = ClusterManager.OnClusterItemClickListener<MarkerIqos> {
            PlaceBottomSheetDialog.newInstance(it.pointEntity.id)
                .show(fragmentManager ?: return@OnClusterItemClickListener false, null)
            true
        }
        cmSticks.setOnClusterItemClickListener(listener)
        cmDevices.setOnClusterItemClickListener(listener)
        cmAccessories.setOnClusterItemClickListener(listener)
        cmService.setOnClusterItemClickListener(listener)
        cmFriendly.setOnClusterItemClickListener(listener)
        cmBase.setOnClusterItemClickListener(listener)

        googleMap?.setOnMarkerClickListener {
            cmSticks.onMarkerClick(it)
            cmDevices.onMarkerClick(it)
            cmAccessories.onMarkerClick(it)
            cmService.onMarkerClick(it)
            cmFriendly.onMarkerClick(it)
            cmBase.onMarkerClick(it)
            true
        }
    }

}

