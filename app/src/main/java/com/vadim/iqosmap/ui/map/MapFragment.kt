package com.vadim.iqosmap.ui.map


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
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
import com.vadim.iqosmap.BuildConfig
import com.vadim.iqosmap.R
import com.vadim.iqosmap.base.BaseFragment
import com.vadim.iqosmap.databinding.FragmentMapBinding
import com.vadim.iqosmap.ui.filter.FilterFragment
import com.vadim.iqosmap.ui.filter.IFilterCallBack
import com.vadim.iqosmap.ui.place.PlaceBottomSheetDialog
import com.vadim.iqosmap.utils.extentions.addFinishListner
import com.vadim.iqosmap.utils.extentions.radius
import com.vadim.iqosmap.utils.extentions.toLatLng
import com.vadim.iqosmap.utils.marker.FilteredClusterManager
import com.vadim.iqosmap.utils.marker.MarkerIqos
import com.vadim.iqosmap.utils.marker.OwnIconRender
import com.vadim.iqosmap.utils.ui.ConstraintSetUtils
import com.vadim.iqosmap.utils.ui.UiUtils

private const val FRAGMENT_LAYOUT_ID = R.layout.fragment_map
private const val REQUEST_CHECK_SETTINGS = 1111
private const val MARKER_ZOOM = 16.0f

@SuppressLint("RestrictedApi")
class MapFragment : BaseFragment<MapViewModel, FragmentMapBinding>(), OnMapReadyCallback, IFilterCallBack {

    override val layoutId = FRAGMENT_LAYOUT_ID

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

    override fun createViewModel() = provideViewModel { MapViewModel(it) }

    override fun setDataToBinding() {
        binding.viewModel = viewModel
    }

    override fun onLowMemory() {
        super.onLowMemory()

        binding.map.onLowMemory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(MapLifecycleObserver(binding.map, fusedLocationClient, locationCallback))
        initMap(savedInstanceState)
    }

    override fun onMapReady(map: GoogleMap) {
        this.googleMap = map

        setListeners()
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

    private fun setListeners() {
        binding.fabFilter.setOnClickListener {
            val fragment = fragmentManager?.findFragmentByTag(FilterFragment.TAG)
            if (fragment == null)
                showFilterFragment()
            else
                hideFilterFragment()
        }

        binding.fabPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${BuildConfig.IQOS_PHONE}")
            startActivity(intent)
        }

        binding.fabWeb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(BuildConfig.IQOS_SITE)
            startActivity(intent)
        }
    }

    private fun observeFilters() {
        viewModel.ldFilters.observe(this, Observer {
            googleMap?.let { map ->
                viewModel.loadPoints(map.cameraPosition.target, map.radius)
            }
        })
    }

    private fun showFilterFragment() {
        val cs = ConstraintSet().apply { clone(binding.clRoot) }

        ConstraintSetUtils.centerView(R.id.fabFilter, cs)

        val transition = ChangeBounds()
        transition.addFinishListner {
            binding.flFilter.visibility = View.VISIBLE

            val filterFragment = FilterFragment.newInstance()

            childFragmentManager.beginTransaction()
                .add(R.id.flFilter, filterFragment, FilterFragment.TAG)
                .commit()

            binding.fabFilter.visibility = View.GONE
        }

        TransitionManager.beginDelayedTransition(binding.clRoot, transition)

        cs.applyTo(binding.clRoot)
    }

    override fun hideFilterFragment() {
        val onFinishEvent = {
            binding.fabFilter.visibility = View.VISIBLE

            val margin = resources.getDimensionPixelOffset(R.dimen.double_margin)
            val cs = ConstraintSet().apply { clone(binding.clRoot) }

            ConstraintSetUtils.bottomLeftView(R.id.fabFilter, cs, margin, margin)

            TransitionManager.beginDelayedTransition(binding.clRoot)

            cs.applyTo(binding.clRoot)
        }

        UiUtils.hideFragmentWithReveal(childFragmentManager, binding.flFilter, FilterFragment.TAG, onFinishEvent)
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

