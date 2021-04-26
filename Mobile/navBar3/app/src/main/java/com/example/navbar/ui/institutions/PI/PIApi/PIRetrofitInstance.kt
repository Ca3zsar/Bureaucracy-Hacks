package com.example.navbar.ui.institutions.PI.PIApi

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

    val api : PISimpleApi by lazy {
        retrofit.create(PISimpleApi::class.java)
    }
}