package com.example.navbar.ui.generareMap.mapApi

import com.example.navbar.ui.generareMap.mapModel.mapResponse
import com.example.navbar.ui.generareMap.mapModel.mapRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface mapSimpleApi {

    @POST("https://bureaucracyhackshostat.herokuapp.com/generateroute/")
    suspend fun pushPost(
            @Body post : mapRequest
    ): Response<mapResponse>
}