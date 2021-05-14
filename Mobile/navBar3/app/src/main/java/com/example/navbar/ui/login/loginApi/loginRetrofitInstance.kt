package com.example.navbar.ui.login.loginApi

import com.example.navbar.ui.login.loginUtil.loginConstants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class loginRetrofitInstance {

    companion object {
        val BASE_URL = "https://bureaucracyhackshostat.herokuapp.com/"
        fun getRetrofitInstance():Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(loginConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: loginSimpleApi by lazy {
            retrofit.create(loginSimpleApi::class.java)
        }
    }
}