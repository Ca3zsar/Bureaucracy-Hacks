package com.example.navbar.ui.login.loginRepository

import com.example.navbar.ui.login.loginApi.loginRetrofitInstance
import com.example.navbar.ui.login.loginModel.lgnReques
import com.example.navbar.ui.login.loginModel.loginPost
import retrofit2.Response

class lgnRepository {
    //suspend fun getPost(): Response<Post> {
    //    return RetrofitInstance.api.getPost()
    //}

    suspend fun pushPost(post : loginPost): Response<lgnReques> {
        return loginRetrofitInstance.api.pushPost(post)
    }
}