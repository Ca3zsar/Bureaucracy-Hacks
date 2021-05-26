package com.example.navbar.ui.institutions.PI.PIApi

import com.example.navbar.ui.institutions.IPJI.IPJIApi.IPJIRetrofitInstance
import com.example.navbar.ui.institutions.IPJI.IPJIApi.IPJISimpleApi
import com.example.navbar.ui.institutions.IPJI.IPJIUtil.IPJIConstants
import com.example.navbar.ui.institutions.PI.PIUtil.PIConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PIRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(PIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val retrofit2 by lazy {
        Retrofit.Builder()
            .baseUrl(PIConstants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : PISimpleApi by lazy {
        retrofit.create(PISimpleApi::class.java)
    }

    val api2 : PISimpleApi by lazy {
        retrofit2.create(PISimpleApi::class.java)
    }
}