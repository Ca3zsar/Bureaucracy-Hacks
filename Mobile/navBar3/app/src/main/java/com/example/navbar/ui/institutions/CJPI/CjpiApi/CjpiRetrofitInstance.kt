package com.example.navbar.ui.institutions.CJPI.CjpiApi

import com.example.navbar.ui.institutions.CJPI.CjpiUtil.CjpiConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CjpiRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(CjpiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val retrofit2 by lazy {
        Retrofit.Builder()
            .baseUrl(CjpiConstants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : CjpiSimpleApi by lazy {
        retrofit.create(CjpiSimpleApi::class.java)
    }

    val api2 : CjpiSimpleApi by lazy {
        CjpiRetrofitInstance.retrofit2.create(CjpiSimpleApi::class.java)
    }
}