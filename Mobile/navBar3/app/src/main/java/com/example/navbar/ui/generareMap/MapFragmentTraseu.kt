package com.example.navbar.ui.generareMap

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.BuildConfig
import com.example.navbar.R
import com.example.navbar.ui.ComunicatorStaticClass
import com.example.navbar.ui.generareMap.mapModel.mapRequest
import com.example.navbar.ui.generareMap.mapRepository.mpRepository
import com.example.navbar.ui.generareTraseu.genTraseuFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.location.LocationSource
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.routing.OnlineRoutingApi
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.route.*
import com.tomtom.online.sdk.traffic.OnlineTrafficApi
import kotlinx.android.synthetic.main.fragment_user_profile.*
import java.util.jar.Manifest
import kotlin.properties.Delegates


class MapFragmentTraseu : Fragment(), OnMapReadyCallback {

    private val routingApi =
        this.context?.let { OnlineRoutingApi.create(it, BuildConfig.ROUTING_API_KEY) }
    val trafficApi = context?.let { OnlineTrafficApi.create(it, BuildConfig.ROUTING_API_KEY) }
    private lateinit var map: TomtomMap
    private var PERMISSION_ID = 1000
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var waypoints: MutableList<LatLng> = mutableListOf()
    var userLatitude: Double = 0.0
    var userLongitude: Double = 0.0

    companion object {
        fun newInstance() = MapFragmentTraseu()
    }

    private lateinit var mapViewModel: MapFragmentTraseuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mapRepository = mpRepository()
        val viewModelFactory = MapFragmentViewModelFactory(mapRepository)

        mapViewModel =
            ViewModelProvider(this, viewModelFactory).get(MapFragmentTraseuViewModel::class.java)

        val root = inflater.inflate(R.layout.map_fragment_traseu_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_map)
        mapViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapViewModel = ViewModelProvider(this).get(MapFragmentTraseuViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment?
        mapFragment?.getAsyncMap(this)

    }


    private fun CheckPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isLocationEnable(): Boolean {
        var locationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "You Have the Permission")
            }
        }
    }


    override fun onMapReady(tomtomMap: TomtomMap) {
        view?.findViewById<TextView>(R.id.text_map)
        map = tomtomMap

        if (CheckPermission()) {
            if (isLocationEnable()) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {

                    } else {
                        Log.d(
                            "YourCurrent location is",
                            location.latitude.toString() + " " + location.longitude.toString()
                        )
                        userLatitude = location.latitude
                        userLongitude = location.longitude

                        val myPost =
                            mapRequest(
                                userLongitude.toString(),
                                userLatitude.toString(),
                                ComunicatorStaticClass.idInstitutie,
                                ComunicatorStaticClass.acteNecesare
                            )
                        mapViewModel.pushPost(myPost)
                        mapViewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
                            if (response.isSuccessful) {
                                Log.d("Response", response.code().toString())


                                for (i in response.body()?.features?.indices!!) {
                                    val features: Map<String, Any> =
                                        response.body()?.features?.get(i) as Map<String, Any>
                                    val geometry: Map<String, Any> =
                                        features.getValue("geometry") as Map<String, Any>
                                    val coordinates: List<Any> =
                                        geometry.getValue("coordinates") as List<Any>
                                    if (coordinates.size == 2) {
                                        val markerBuilder = MarkerBuilder(
                                            LatLng(
                                                coordinates[1] as Double, coordinates[0] as Double
                                            )
                                        )
                                            .iconAnchor(MarkerAnchor.Bottom)
                                            .decal(true)
                                        tomtomMap.addMarker(markerBuilder)
                                    } else if (coordinates.size > 2) {
                                        Log.d("bug", coordinates.toString())
                                        for (i in coordinates.indices) {
                                            val pair: List<Double> = coordinates[i] as List<Double>
                                            waypoints.add(LatLng(pair[1], pair[0]))
                                        }
                                        val iasi = LatLng(userLatitude, userLongitude)
                                        var pair: List<Double> = coordinates[coordinates.size - 1] as List<Double>
                                        val iasi2 = LatLng(pair[1], pair[0])
                                        SimpleMarkerBalloon("Iasi")
                                        tomtomMap.centerOn(
                                            CameraPosition.builder().focusPosition(iasi).zoom(15.0)
                                                .build()
                                        )

                                        val routingApi = this.context?.let { it1 ->
                                            OnlineRoutingApi.create(
                                                it1,
                                                BuildConfig.ROUTING_API_KEY
                                            )
                                        }

                                        val routeDescriptor = RouteDescriptor.Builder()
                                            .routeType(com.tomtom.online.sdk.routing.route.description.RouteType.FASTEST)
                                            .build()
                                        Log.d("Response", waypoints.toString())
                                        val routeCalculationDescriptor =
                                            RouteCalculationDescriptor.Builder()
                                                .routeDescription(routeDescriptor)
                                                .supportingPoints(waypoints)
                                                .build()
                                        val routeSpecification =
                                            RouteSpecification.Builder(iasi, iasi2)
                                                .routeCalculationDescriptor(
                                                    routeCalculationDescriptor
                                                )
                                                .build()

                                        if (routingApi != null) {
                                            routingApi.planRoute(
                                                routeSpecification,
                                                object : RouteCallback {
                                                    override fun onError(error: RoutingException) {
                                                        Toast.makeText(
                                                            context,
                                                            "text",
                                                            Toast.LENGTH_LONG
                                                        )
                                                            .show()
                                                    }

                                                    override fun onSuccess(routePlan: RoutePlan) {
                                                        for (fullRoute in routePlan.routes) {
                                                            val routeBuilder = RouteBuilder(
                                                                fullRoute.getCoordinates()
                                                            )
                                                                .endIcon(context?.let {
                                                                    Icon.Factory.fromResources(
                                                                        it,
                                                                        R.drawable.ic_map_route_destination
                                                                    )
                                                                })
                                                                .startIcon(context?.let {
                                                                    Icon.Factory.fromResources(
                                                                        it,
                                                                        R.drawable.ic_map_route_departure
                                                                    )
                                                                })
                                                            map.addRoute(routeBuilder)
                                                        }
                                                    }
                                                })
                                        }

                                        tomtomMap.set2DMode()
                                        val button = view?.findViewById<Button>(R.id.button_back)
                                        if (button != null) {
                                            button.setOnClickListener {
                                                val nextFrag = genTraseuFragment()
                                                activity?.supportFragmentManager?.beginTransaction()
                                                    ?.replace(R.id.nav_host_fragment, nextFrag)
                                                    ?.addToBackStack(null)?.commit()
                                            }
                                        }

                                    }
                                }
                            } else {
                                Log.d("test", myPost.toString())
                                Log.d("Error", response.errorBody().toString())
                            }
                        })
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please Enable your Location service",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}