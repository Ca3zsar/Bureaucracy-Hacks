package com.example.navbar.ui.generareMap.mapRepository

import com.example.navbar.ui.generareMap.mapApi.mapRetrofitInstance
import com.example.navbar.ui.generareMap.mapModel.mapResponse
import com.example.navbar.ui.generareMap.mapModel.mapRequest
import retrofit2.Response

class mpRepository {
    suspend fun pushPost(post : mapRequest): Response<mapResponse> {
        return mapRetrofitInstance.api.pushPost(post)
    }
}