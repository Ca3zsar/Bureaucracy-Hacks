package com.example.navbar.ui.institutions.PI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.PI.PIRepository.PIRepository

class PIViewModelFactory (
        private val repository: PIRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PIViewModel(repository) as T
    }
}