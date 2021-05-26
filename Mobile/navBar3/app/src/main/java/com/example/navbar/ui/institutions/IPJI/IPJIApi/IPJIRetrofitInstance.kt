package com.example.navbar.ui.institutions.IPJI.IPJIApi

import com.example.navbar.ui.institutions.DASI.DasiApi.DasiRetrofitInstance
import com.example.navbar.ui.institutions.DASI.DasiApi.DasiSimpleApi
import com.example.navbar.ui.institutions.DASI.DasiUtil.DasiConstants
import com.example.navbar.ui.institutions.IPJI.IPJI
import com.example.navbar.ui.institutions.IPJI.IPJIUtil.IPJIConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object IPJIRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(IPJIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val retrofit2 by lazy {
        Retrofit.Builder()
            .baseUrl(IPJIConstants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : IPJISimpleApi by lazy {
        retrofit.create(IPJISimpleApi::class.java)
    }

    val api2 : IPJISimpleApi by lazy {
        retrofit2.create(IPJISimpleApi::class.java)
    }
}