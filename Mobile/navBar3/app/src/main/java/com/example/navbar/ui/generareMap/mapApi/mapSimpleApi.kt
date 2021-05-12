package com.example.navbar.ui.generareMap.mapApi

import com.example.navbar.ui.generareMap.mapModel.mapPost
import com.example.navbar.ui.generareMap.mapModel.mapResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface mapSimpleApi {

    @POST("https://bureaucracyhackshostat.herokuapp.com/generateRoute/")
    suspend fun pushPost(
            @Body post : mapResponse
    ): Response<mapPost>
}