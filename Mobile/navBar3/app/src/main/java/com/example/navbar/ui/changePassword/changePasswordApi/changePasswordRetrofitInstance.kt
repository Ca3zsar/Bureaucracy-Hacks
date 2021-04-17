package com.example.navbar.ui.changePassword.changePasswordApi

import com.example.navbar.ui.changePassword.changePasswordUtil.changePasswordConstants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object changePasswordRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : changePasswordSimpleApi by lazy {
        retrofit.create(changePasswordSimpleApi::class.java)
    }
}