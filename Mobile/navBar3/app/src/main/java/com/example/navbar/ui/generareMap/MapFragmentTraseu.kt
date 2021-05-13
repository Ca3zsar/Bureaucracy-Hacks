package com.example.navbar.ui.generareMap

import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.BuildConfig
import com.example.navbar.R
import com.example.navbar.ui.generareMap.mapModel.mapRequest
import com.example.navbar.ui.generareMap.mapRepository.mpRepository
import com.example.navbar.ui.generareTraseu.genTraseuFragment
import com.google.android.gms.location.LocationListener
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.location.LocationSource
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.routing.OnlineRoutingApi
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.route.*
import com.tomtom.online.sdk.traffic.OnlineTrafficApi


class MapFragmentTraseu : Fragment(), OnMapReadyCallback {

    private val routingApi = this.context?.let { OnlineRoutingApi.create(it, BuildConfig.ROUTING_API_KEY) }
    val trafficApi = context?.let { OnlineTrafficApi.create(it, BuildConfig.ROUTING_API_KEY) }
    private lateinit var map: TomtomMap
    private lateinit var waypoints : List<LatLng>
    private var route: Route? = null
    private var departurePosition: LatLng? = null
    private val PERMISSION_REQUEST_LOCATION = 0
    private lateinit var locationSource: LocationSource
    private var latLngCurrentPosition: LatLng? = null
    private val mLocationManager: LocationManager? = null

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
        mapViewModel = ViewModelProvider(this, viewModelFactory).get(MapFragmentTraseuViewModel::class.java)

        val root = inflater.inflate(R.layout.map_fragment_traseu_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_map)
        mapViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapViewModel = ViewModelProvider(this).get(MapFragmentTraseuViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment?
        mapFragment?.getAsyncMap(this)

    }

    /*inner class MylocationListener() : LocationListener {
        init {
            location= Location("me")
            location!!.longitude
            location!!.latitude
        }

        override fun onLocationChanged(location1: Location?) {
            location=location1
        }

    }*/

    override fun onMapReady(tomtomMap: TomtomMap) {
        val textView = view?.findViewById<TextView>(R.id.text_map)
        map = tomtomMap

        map.isMyLocationEnabled = true
        val location = map.userLocation

        

        if (map.userLocation != null) {
            //val currentLatitude = map.userLocation!!.latitude
            //val currentLongitude = map.userLocation!!.longitude
            //val currentLocation = LatLng(currentLatitude, currentLongitude)
            Log.d("Response", LatLng(map.userLocation!!.latitude, map.userLocation!!.longitude).toString())
            //tomtomMap.centerOn(CameraPosition.builder().focusPosition(currentLocation).zoom(15.0).build())
        }
        else{
            Log.d("caca", location.toString())
        }

        /*val test1 = listOf("declaratie de inregistrare fiscala – formular 010 sau formular 040 (pentru institutii publice), care se obtine gratuit de la etajul 1, camera 1103;", "copie de pe autorizatia de functionare eliberata de autoritatea competenta sau de pe actul legal de infiintare, dupa caz;", "dovada detinerii sediului (act de proprietate, contract de inchiriere si alte asemenea);", "copie act de identitate reprezentant legal;", "alte acte doveditoare, dupa caz:", "copie autentificata statut;", "proces verbal de constituire;", "acord de asociere; ", "hotarire judecatoreasca de infiintare;", "dovada spatiu;", "adresa de la organul financiar local privind inregistrarea asociatiei de proprietari.")

        val myPost = mapRequest("27.55111602208522", "47.14482900924825", "42", test1)
        mapViewModel.pushPost(myPost)
        mapViewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.code().toString())
                Log.d("yey", response.body().toString())
                Toast.makeText(activity, response.code().toString(), Toast.LENGTH_LONG).show()
            } else {
                Log.d("test", myPost.toString())
                Log.d("Error", response.errorBody().toString())
            }
        })*/

        /*map.trafficSettings.turnOnTrafficIncidents()
        map.trafficSettings.turnOnTrafficFlowTiles()
        map.trafficSettings.turnOffTrafficIncidents()
        map.trafficSettings.turnOffTrafficFlowTiles()*/

        val iasi = LatLng(47.17, 27.57)
        val iasi2 = LatLng(47.17, 27.58)
        val balloon = SimpleMarkerBalloon("Iasi")
        tomtomMap.centerOn(CameraPosition.builder().focusPosition(iasi).zoom(15.0).build())

        val routingApi = this.context?.let { it1 -> OnlineRoutingApi.create(it1, BuildConfig.ROUTING_API_KEY) }
        val routeDescriptor = RouteDescriptor.Builder()
            .routeType(com.tomtom.online.sdk.routing.route.description.RouteType.FASTEST)
            .build()
        val routeCalculationDescriptor = RouteCalculationDescriptor.Builder()
            .routeDescription(routeDescriptor).build()
        val routeSpecification = RouteSpecification.Builder(iasi, iasi2)
            .routeCalculationDescriptor(routeCalculationDescriptor)
            .build()
        if (routingApi != null) {
            routingApi.planRoute(routeSpecification, object : RouteCallback {
                override fun onError(error: RoutingException) {
                    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(routePlan: RoutePlan) {
                    for (fullRoute in routePlan.routes) {
                        val routeBuilder = RouteBuilder(
                            fullRoute.getCoordinates())
                            .endIcon(context?.let { Icon.Factory.fromResources(it, R.drawable.ic_map_route_destination) })
                            .startIcon(context?.let { Icon.Factory.fromResources(it, R.drawable.ic_map_route_departure) })
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
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, nextFrag)?.addToBackStack(null)?.commit()
            }
        }
    }
}