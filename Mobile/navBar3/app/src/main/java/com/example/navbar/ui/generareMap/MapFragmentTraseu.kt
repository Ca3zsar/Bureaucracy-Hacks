package com.example.navbar.ui.generareMap

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.BuildConfig
import com.example.navbar.R
import com.example.navbar.ui.generareTraseu.TraseuFragment
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.*
import com.tomtom.online.sdk.routing.OnlineRoutingApi
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.route.*
import com.tomtom.online.sdk.traffic.OnlineTrafficApi



class MapFragmentTraseu : Fragment(), OnMapReadyCallback {

    private val routingApi = this.context?.let { OnlineRoutingApi.create(it, BuildConfig.ROUTING_API_KEY) }
    val trafficApi = context?.let { OnlineTrafficApi.create(it, BuildConfig.ROUTING_API_KEY) }
    private lateinit var map: TomtomMap
    private var route: Route? = null

    companion object {
        fun newInstance() = MapFragmentTraseu()
    }

    private lateinit var viewModel: MapFragmentTraseuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment_traseu_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapFragmentTraseuViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as MapFragment?
        mapFragment?.getAsyncMap(this)

    }
    override fun onMapReady(tomtomMap: TomtomMap) {

        tomtomMap.isMyLocationEnabled = true
        val location = tomtomMap.userLocation

        map = tomtomMap
        map.trafficSettings.turnOnTrafficIncidents()
        map.trafficSettings.turnOnTrafficFlowTiles()
        map.trafficSettings.turnOffTrafficIncidents()
        map.trafficSettings.turnOffTrafficFlowTiles()

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
                        map?.addRoute(routeBuilder)
                    }
                }
            })
        }
        tomtomMap.set2DMode()
        val button = view?.findViewById<Button>(R.id.button_back)
        if (button != null) {
            button.setOnClickListener {
                val nextFrag = TraseuFragment()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, nextFrag)?.addToBackStack(null)?.commit()
            }
        }
    }
}