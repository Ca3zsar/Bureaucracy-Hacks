package com.example.navbar.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.register.registerRepostory.rgstrRepostory

class registerViewModelFactory(
        private val repository: rgstrRepostory
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return registerViewModel(repository) as T
    }
}