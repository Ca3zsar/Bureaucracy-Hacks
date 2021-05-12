package com.example.navbar.ui.generareMap.mapRepository

import com.example.navbar.ui.generareMap.mapApi.mapRetrofitInstance
import com.example.navbar.ui.generareMap.mapModel.mapPost
import com.example.navbar.ui.generareMap.mapModel.mapResponse
import retrofit2.Response

class mpRepository {
    suspend fun pushPost(post : mapResponse): Response<mapPost> {
        return mapRetrofitInstance.api.pushPost(post)
    }
}