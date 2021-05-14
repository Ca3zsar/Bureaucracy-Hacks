package com.example.navbar.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.login.loginRepository.lgnRepository

class loginViewModelFactory(
    private val repository: lgnRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return loginViewModel(repository) as T
    }
}