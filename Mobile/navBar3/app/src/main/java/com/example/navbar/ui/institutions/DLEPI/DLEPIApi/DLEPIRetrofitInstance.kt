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

    val api : DLEPISimpleApi by lazy {
        retrofit.create(DLEPISimpleApi::class.java)
    }
}