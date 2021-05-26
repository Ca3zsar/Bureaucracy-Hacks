package com.example.navbar.ui.institutions.SALUBRIS.SalubrisApi

import com.example.navbar.ui.institutions.PI.PIApi.PIRetrofitInstance
import com.example.navbar.ui.institutions.PI.PIApi.PISimpleApi
import com.example.navbar.ui.institutions.PI.PIUtil.PIConstants
import com.example.navbar.ui.institutions.SALUBRIS.SALUBRIS
import com.example.navbar.ui.institutions.SALUBRIS.SalubrisUtil.SalubrisConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SalubrisRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SalubrisConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofit2 by lazy {
        Retrofit.Builder()
            .baseUrl(SalubrisConstants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : SalubrisSimpleApi by lazy {
        retrofit.create(SalubrisSimpleApi::class.java)
    }

    val api2 : SalubrisSimpleApi by lazy {
        retrofit2.create(SalubrisSimpleApi::class.java)
    }
}