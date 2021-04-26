package com.example.navbar.ui.institutions.IPJI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.IPJI.IPJIRepository.IPJIRepository

class IPJIViewModelFactory (
        private val repository: IPJIRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IPJIViewModel(repository) as T
    }
}