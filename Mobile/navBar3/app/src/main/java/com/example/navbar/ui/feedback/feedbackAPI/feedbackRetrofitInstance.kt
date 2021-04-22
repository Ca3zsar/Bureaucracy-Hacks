package com.example.navbar.ui.feedback.feedbackAPI

import android.provider.SyncStateContract
import com.example.navbar.ui.feedback.feedbackUtil.feedbackConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object feedbackRetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(feedbackConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : feedbackSimpleApi by lazy {
        retrofit.create(feedbackSimpleApi::class.java)
    }
}