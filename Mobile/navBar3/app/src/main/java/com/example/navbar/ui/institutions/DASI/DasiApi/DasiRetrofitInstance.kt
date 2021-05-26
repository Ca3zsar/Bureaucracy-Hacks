package com.example.navbar.ui.institutions.DASI.DasiApi

import com.example.navbar.ui.institutions.DASI.DasiUtil.DasiConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DasiRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(DasiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private val retrofit2 by lazy {
        Retrofit.Builder()
            .baseUrl(DasiConstants.BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : DasiSimpleApi by lazy {
        retrofit.create(DasiSimpleApi::class.java)
    }

    val api2 : DasiSimpleApi by lazy {
        retrofit2.create(DasiSimpleApi::class.java)
    }
}