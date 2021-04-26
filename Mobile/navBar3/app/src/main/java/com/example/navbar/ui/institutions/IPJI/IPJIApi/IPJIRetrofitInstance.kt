package com.example.navbar.ui.institutions.IPJI.IPJIApi

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

    val api : IPJISimpleApi by lazy {
        retrofit.create(IPJISimpleApi::class.java)
    }
}