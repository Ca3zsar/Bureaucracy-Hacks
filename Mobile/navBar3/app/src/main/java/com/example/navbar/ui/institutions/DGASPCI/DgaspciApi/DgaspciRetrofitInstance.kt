package com.example.navbar.ui.institutions.DGASPCI.DgaspciApi

import com.example.navbar.ui.institutions.DASI.DasiApi.DasiRetrofitInstance
import com.example.navbar.ui.institutions.DASI.DasiApi.DasiSimpleApi
import com.example.navbar.ui.institutions.DASI.DasiUtil.DasiConstants
import com.example.navbar.ui.institutions.DGASPCI.DgaspciUtil.DgaspciConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DgaspciRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(DgaspciConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val retrofit2 by lazy {
        Retrofit.Builder()
            .baseUrl(DgaspciConstants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : DgaspciSimpleApi by lazy {
        retrofit.create(DgaspciSimpleApi::class.java)
    }

    val api2 : DgaspciSimpleApi by lazy {
        retrofit2.create(DgaspciSimpleApi::class.java)
    }
}