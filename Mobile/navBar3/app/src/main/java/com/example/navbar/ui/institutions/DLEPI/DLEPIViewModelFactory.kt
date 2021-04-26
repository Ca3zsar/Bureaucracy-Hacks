package com.example.navbar.ui.institutions.DLEPI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.DLEPI.DLEPIRepository.DLEPIRepository

class DLEPIViewModelFactory (
        private val repository: DLEPIRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DLEPIViewModel(repository) as T
    }
}