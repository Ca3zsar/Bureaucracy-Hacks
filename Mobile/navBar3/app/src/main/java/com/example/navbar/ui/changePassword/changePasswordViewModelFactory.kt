package com.example.navbar.ui.changePassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.changePassword.changePasswordRepository.changePasswordRepository

class changePasswordViewModelFactory(
    private val repository: changePasswordRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return changePasswordViewModel(repository) as T
    }
}