package com.example.navbar.ui.institutions.DLEPI.DLEPIApi

import com.example.navbar.ui.institutions.DLEPI.DLEPIUtil.DLEPIConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DLEPIRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(DLEPIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val retrofit2 by lazy {
        Retrofit.Builder()
            .baseUrl(DLEPIConstants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : DLEPISimpleApi by lazy {
        retrofit.create(DLEPISimpleApi::class.java)
    }

    val api2 : DLEPISimpleApi by lazy {
        retrofit2.create(DLEPISimpleApi::class.java)
    }
}