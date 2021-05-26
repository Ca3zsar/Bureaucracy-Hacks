package com.example.navbar.ui.institutions.DGpP.DGpPApi

import com.example.navbar.ui.institutions.DGpP.DGpPUtil.DGpPConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DGpPRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(DGpPConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val retrofit2 by lazy {
        Retrofit.Builder()
            .baseUrl(DGpPConstants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : DGpPSimpleApi by lazy {
        retrofit.create(DGpPSimpleApi::class.java)
    }

    val api2 : DGpPSimpleApi by lazy {
        retrofit2.create(DGpPSimpleApi::class.java)
    }
}