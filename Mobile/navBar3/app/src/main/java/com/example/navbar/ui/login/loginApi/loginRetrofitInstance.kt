package com.example.navbar.ui.login.loginApi

import com.example.navbar.ui.login.loginUtil.loginConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object loginRetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(loginConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api : loginSimpleApi by lazy {
        retrofit.create(loginSimpleApi::class.java)
    }
}