package com.example.navbar.ui.register.registerApi

import com.example.navbar.ui.register.registerUtil.registerConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object registerRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(registerConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api : registerSimpleApi by lazy {
        retrofit.create(registerSimpleApi::class.java)
    }
}