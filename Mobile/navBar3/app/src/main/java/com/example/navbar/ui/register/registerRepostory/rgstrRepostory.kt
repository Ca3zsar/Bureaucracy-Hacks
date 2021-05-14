package com.example.navbar.ui.register.registerRepostory

import com.example.navbar.ui.changePassword.changePasswordApi.changePasswordRetrofitInstance.api
import com.example.navbar.ui.register.registerApi.registerRetrofitInstance
import com.example.navbar.ui.register.registerModel.registerPost
import retrofit2.Response

class rgstrRepostory {
    //suspend fun getPost(): Response<Post> {
    //    return RetrofitInstance.api.getPost()
    //}

    suspend fun pushPost(post : registerPost): Response<registerPost> {
        return registerRetrofitInstance.api.pushPost(post)
    }
}