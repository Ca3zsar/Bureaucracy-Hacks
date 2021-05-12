package com.example.navbar.ui.generareMap.mapApi

import com.example.navbar.ui.generareMap.mapUtil.mapConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object mapRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(mapConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api : mapSimpleApi by lazy {
        retrofit.create(mapSimpleApi::class.java)
    }
}